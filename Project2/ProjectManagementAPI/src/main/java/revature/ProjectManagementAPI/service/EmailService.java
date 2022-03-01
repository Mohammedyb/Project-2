package revature.ProjectManagementAPI.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.ProjectRepository;
import revature.ProjectManagementAPI.DAO.UserRepository;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EmailService {
    /** Application name. */
    private static String APPLICATION_NAME;
    /** Global instance of the JSON factory. */
    private static JsonFactory JSON_FACTORY;
    /** Directory to store authorization tokens for this application. */
    private static String TOKENS_DIRECTORY_PATH;
    private static List<String> SCOPES;
    private static String CREDENTIALS_FILE_PATH;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public EmailService(ProjectRepository projectRepository, MasterService masterService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        APPLICATION_NAME = "ProjectManagementAPI";
        JSON_FACTORY = GsonFactory.getDefaultInstance();
        TOKENS_DIRECTORY_PATH = "tokens";
        CREDENTIALS_FILE_PATH = "/credentials.json";
        SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = EmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public String createCalendar(Project project) throws IOException, GeneralSecurityException {
        String name = project.getName();

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        //Create the calendar itself
        com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary(name);
        calendar.setDescription("Calendar created to support the work done by project team " + name + ".");

        //Throw the calendar up into the GCP
        com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();
        return createdCalendar.getId();
    }

    /**
     *
     * @param meeting the meeting to be made into a google calendar event!
     * @return an html link to the event!
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public String createMeeting(Meeting meeting) throws GeneralSecurityException, IOException {
        Project project = projectRepository.getById(meeting.getProjectId());

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        /* ==== Setting the date/length ==== */
        DateTime startDate = new DateTime(meeting.getTimestamp().getTime());
        EventDateTime start = new EventDateTime().setDateTime(startDate);
        if(meeting.getMeetingLength() == 0) { //Trying to check for null - if it is null, set it to the default of 1.5 hours
            meeting.setMeetingLength(1.5);
        }
        DateTime endDate = new DateTime(startDate.getValue()+ 3600000L * Double.valueOf(meeting.getMeetingLength()).longValue());
        EventDateTime end = new EventDateTime().setDateTime(endDate);

        /* ==== Setting the recurrence ==== */
        // the default below is to be a weekly event - work with Kramer to figure out how they'll input their recurrence rules
        String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;UNTIL=20251231T115959Z"};

        /* ==== Setting the attendees ==== */
        //A method to grab all attendees to a meeting here
        String[] attendeeEmails;
        EventAttendee[] attendees = new EventAttendee[] { //Automatically add only our email and the email of the project's manager - we'll have a method to add attendees afterwards
                new EventAttendee().setEmail("project02sender@gmail.com"),
                new EventAttendee().setEmail(userRepository.getUserByID(projectRepository.getProjectById(meeting.getProjectId()).getProjectManagerId()).getEmail())
        };
        /* ==== Setting the reminders ==== */
        //Default is an email reminder 24 hours before and one hour before
        //Do we even want to change that?
        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24*60),
                new EventReminder().setMethod("email").setMinutes(60)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));

        /* ==== Setting the calendar ID ==== */
        String calID = project.getMeetingCalendarId();

        /* ==== Bringing it all together ==== */
        Event createdEvent = new Event()
                .setSummary("Team Meeting") //will be set to the meeting type when we've got an enum for that
                .setDescription("A meeting for team " + calID + ".")
                .setStart(start)
                .setEnd(end)
                .setRecurrence(Arrays.asList(recurrence))
                .setAttendees(Arrays.asList(attendees))
                .setReminders(reminders);

        /* ==== execute! (throw it into the calendar service) ==== */
        createdEvent = service.events().insert(calID, createdEvent).execute();
        service.events().get(calID, createdEvent.getId());

        return createdEvent.getHtmlLink();
    }

    //PUT https://www.googleapis.com/calendar/v3/calendars/calendarId/events/eventId
    public void inviteAttendee(String calID, List<String> newAttendees) {

    }
}
