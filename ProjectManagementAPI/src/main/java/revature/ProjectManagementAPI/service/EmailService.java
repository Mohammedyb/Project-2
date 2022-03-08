package revature.ProjectManagementAPI.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.ProjectRepository;
import revature.ProjectManagementAPI.DAO.UserRepository;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.NewMeetingDTO;
import revature.ProjectManagementAPI.models.Project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service @Slf4j
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

    public EmailService() {
    }

    @Autowired
    public EmailService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        APPLICATION_NAME = "ProjectManagementAPI";
        JSON_FACTORY = GsonFactory.getDefaultInstance();
        TOKENS_DIRECTORY_PATH = "tokens";
        CREDENTIALS_FILE_PATH = "../../../credentials.json";
        SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    /*private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, List<String> SCOPES) throws IOException {
        GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
        clientSecrets.set("client_id", System.getenv("CLIENT_ID"));
        clientSecrets.set("client_secret", System.getenv("CLIENT_SECRET"));
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
    */
    //OLD METHOD
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
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

    /**
     * Updates a specific meeting with new features
     * @param calID the id of the calendar with the event to be updated
     * @param meetingID the specific meeting to be updated
     * @param newMeeting the meeting to replace the old meeting
     * @return the html link to the newly updated calendar event
     */
    public String updateMeeting(String calID, String meetingID, Meeting newMeeting, String recurrence) {
        String token = "";
        try {
            //token = refreshAccessToken(userRepository.getUserByEmail(projectRepository.getProjectByName(calID).getProjectManager()).getRefreshToken(), "https://www.googleapis.com/auth/calendar");
        } catch(Exception e){
            log.error("Project manager for project " + calID + "did not have a refresh token - setting one.");

        }
        return "";
    }

    private String receiveAccessTokens(String accessToken) throws IOException {
        HttpTransport transport = new NetHttpTransport();
        String paramString = "\"client_id\": \"" + System.getenv("CLIENT_ID") + "\"" +
                "\"client_secret\": \"" + System.getenv("CLIENT_SECRET") + "\"" +
                "\"code\": \"" + accessToken + "\"" +
                "\"scope\": \"https://www.googleapis.com/auth/calendar\"" +
                "\"grant_type\": \"authorization_code\"" +
                "\"redirect_uri\": \"http://localhost:8080/calendar\"";
        HttpContent params = new UrlEncodedContent(paramString);
        HttpRequestFactory httpRequestFactory = transport.createRequestFactory();
        HttpRequest tokenReq = httpRequestFactory.buildPostRequest(new GenericUrl("https://oauth2.googleapis.com/token"), params);
        HttpResponse tokenRes = tokenReq.execute();
        String codes = "";
        if(tokenRes.getStatusCode() < 200 || tokenRes.getStatusCode() > 299) { //if it's not successful
            log.error("Unable to generate authorization token");
        } else {
            JsonObjectParser parser = JSON_FACTORY.createJsonObjectParser();
            JsonParser jParse = parser.getJsonFactory().createJsonParser(tokenRes.getContent().toString());
            jParse.skipToKey( ImmutableSet.of("access_token"));
            codes += jParse.getCurrentToken().toString();
            jParse.skipToKey(ImmutableSet.of("refresh_token"));
            codes += "%";
            codes += jParse.getCurrentToken().toString();

        }
        return codes;
    }

    /**
     * Refreshes the OAuth Token of a user, if necessary
     * @param refreshToken the users refresh token
     * @return the access token for the user
     * @throws IOException if they don't have a valid refresh token
     */
    private static String refreshAccessToken(String refreshToken, String scopes) throws IOException {
        if(!refreshToken.equalsIgnoreCase("none")) { //if the user jas a refresh token
            HttpTransport transport = new NetHttpTransport();
            GoogleRefreshTokenRequest tokenRequest = new GoogleRefreshTokenRequest(transport,
                    JSON_FACTORY,
                    refreshToken,
                    System.getenv("CLIENT_ID"),
                    System.getenv("CLIENT_SECRET"));
            GoogleTokenResponse tokenResponse = tokenRequest.execute();
            return tokenResponse.getAccessToken();
        } else {
            return "NONE";
        }
    }

    /**
     *
     * @param meeting the meeting to be made into a google calendar event!
     * @return an html link to the event!
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public String createMeeting(NewMeetingDTO meeting, String userEmail) throws GeneralSecurityException, IOException {
        Project project = projectRepository.getById(meeting.getProjectId());
        log.info("Creating a meeting for the project {}", project.getName());
        log.info("Requesting authorization from google to create new event");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        //Either get or refresh an access token!
        /*String paramString = "client_id=" + System.getenv("CLIENT_ID") + "&" + //url format
                "response_type=code&" +
                "state=state_parameter_passthrough_value&" +
                "scope=https://www.googleapis.com/auth/calendar&" +
                "redirect_uri=http://localhost:8080/newmeeting/google&" +
                "prompt=consent&" +
                "include_granted_scopes=true";
          String paramString = "\"client_id\": \"" + System.getenv("CLIENT_ID") + "\"" + //json format
                "\"response_type\": \"code\"" +
                "\"state\": \"state_parameter_passthrough_value\"" +
                "\"scope\": \"https://www.googleapis.com/auth/calendar\"" +
                "\"redirect_uri\": \"http://localhost:8080/newmeeting/google\"" +
                "\"prompt\": \"consent\"" +
                "\"include_granted_scopes\": \"true\"";
        HttpRequest tokenRequest = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(new GenericUrl("https://accounts.google.com/o/oauth2/v2/auth" + "?" + paramString));
        HttpResponse tokenResponse = tokenRequest.execute();
        int statusCode = tokenResponse.getStatusCode();
        if(statusCode < 200 || statusCode > 299) {
            log.error("Error authorizing user to create a new event");
            throw new IOException("Bad status code: " + statusCode + " error: " + tokenResponse.getStatusMessage());
        } else {
            log.info("Authorization received. Creating new Google event for project " + project.getName());
        }
        String token = CharStreams.toString(new InputStreamReader(tokenResponse.getContent(), Charsets.UTF_8)); */

        // ==== Setting the date/length ====
        DateTime startDate = meeting.getStartDate();
        EventDateTime start = new EventDateTime().setDateTime(startDate);
        start.setTimeZone("America/New_York");
        if(meeting.getMeetingLength() == 0) { //Trying to check for null - if it is null, set it to the default of 1.5 hours
            meeting.setMeetingLength(1.5);
        }
        DateTime endDate = new DateTime(startDate.getValue()+ 3600000L * Double.valueOf(meeting.getMeetingLength()).longValue());
        EventDateTime end = new EventDateTime().setDateTime(endDate);
        end.setTimeZone("America/New_York");
        // ==== Setting the recurrence ====
        // the default below is to be a weekly event - work with Kramer to figure out how they'll input their recurrence rules
        String[] recurrence = new String[] {"RRULE:FREQ=" + meeting.getFreq() + ";"};

        // ==== Setting the attendees ====//meeting.getAttendees().toArray(new String[0]);

        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("project02sender@gmail.com"),
                new EventAttendee().setEmail(userEmail)
        };

        // ==== Setting the reminders ====
        //Default is an email reminder 24 hours before and one hour before
        //Do we even want to change that?
        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24*60),
                new EventReminder().setMethod("email").setMinutes(60),
                new EventReminder().setMethod("popup").setMinutes(10)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));

        // ==== Setting the calendar ID ====
        String calID = "primary";

        // ==== Bringing it all together ====
        Event createdEvent = new Event()
                .setSummary("Team Meeting") //will be set to the meeting type when we've got an enum for that
                .setDescription("A meeting for team " + calID + ".")
                .setStart(start)
                .setEnd(end)
                .setRecurrence(Arrays.asList(recurrence))
                .setAttendees(Arrays.asList(attendees))
                .setReminders(reminders)
                .setGuestsCanInviteOthers(true)
                .setGuestsCanSeeOtherGuests(true)
                .setVisibility("public");

        // ==== execute! (throw it into the calendar service) ====
        createdEvent = service.events().insert(calID, createdEvent).execute();
        service.events().get(calID, createdEvent.getId());

        return createdEvent.getHtmlLink();
    }

    //PUT https://www.googleapis.com/calendar/v3/calendars/calendarId/events/eventId
    /**
     * Invites any number of attendees to a given event
     * @param calID the calendar id for the calendar on which the event takes place
     * @param event the full event object that invites are being sent to
     * @param newAttendees a string list of the emails of new attendees
     * @return the newly updated event, with more invitees
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Event inviteAttendee(String calID, Event event, List<String> newAttendees) throws GeneralSecurityException, IOException {
        log.info("Adding an attendant to the meeting {}", event.getSummary());
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        Events instances = service.events().instances(calID, event.getId()).execute();
        Event instance = instances.getItems().get(instances.getItems().indexOf(event));
        EventAttendee[] attendees = instance.getAttendees().toArray(new EventAttendee[0]);
        List<EventAttendee> attendeeList = Arrays.asList(attendees);
        for(String person : newAttendees )
        {
            attendeeList.add(new EventAttendee().setEmail(person));
        }
        instance.setAttendees(attendeeList);
        return service.events().update(calID, instance.getId(), instance).execute();
    }


}
