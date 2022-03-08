pipeline {
    environment {
        registry = 'mohammedyb/pmapi'
        dockerHubCreds = 'docker_hub'
        dockerImage = ''
        deploymentFile = 'ProjectManagementAPI/k8s/deployment.yml'
    }
    agent any
    stages {
        stage('Test') {
            when {
                branch 'feature/*'
            }
        steps {
            withMaven {
                cleanWs()
                sh 'mvn test'
            }
        }
    }
    stage('Build') {
        when {
            branch 'main'
        }
        steps {
            withMaven {
                sh 'mvn -f ProjectManagementAPI/pom.xml clean install'
                sh 'mvn -f ProjectManagementAPI/pom.xml clean package -DskipTests'
            }
        }
    }
    stage('Docker Build') {
        when {
            branch 'main'
        }
        steps {
            script {
                echo "$registry:$currentBuild.number"
                dockerImage = docker.build ("$registry", "-f ProjectManagementAPI/Dockerfile .")
            }
        }
    }
    stage('Docker Deliver') {
        when {
            branch 'main'
        }
        steps {
            script {
                docker.withRegistry('', dockerHubCreds) {
                    dockerImage.push("$currentBuild.number")
                    dockerImage.push("latest")
                }
            }
        }
    }
    stage('Wait for approval') {
        when {
            branch 'main'
        }
        steps {
            script {
                try {
                    timeout(time: 1, unit: 'MINUTES') {
                        approved = input message: 'Deploy to production?', ok: 'Continue',
                            parameters: [choice(name: 'approved', choices: 'Yes\nNo', description: 'Deploy build to production')]
                        if(approved != 'Yes') {
                            error('Build did not pass approval')
                        }
                    }
                } catch(error) {
                    error('Build failed because timeout was exceeded')
                }
            }
        }
    }
    stage('Deploy to GKE') {
        when {
            branch 'main'
        }
        steps{
            sh 'sed -i "s/%TAG%/$BUILD_NUMBER/g" ./ProjectManagementAPI/k8s/deployment.yml'
            sh 'cat ./ProjectManagementAPI/k8s/deployment.yml'
            step([$class: 'KubernetesEngineBuilder',
                projectId: 'spherical-gate-338602',
                clusterName: 'spherical-gate-338602-gke',
                zone: 'us-central1',
                manifestPattern: 'ProjectManagementAPI/k8s/',
                credentialsId: 'spherical-gate-338602',
                verifyDeployments: true
            ])

            cleanWs()

            discordSend description: "Build #$currentBuild.number",
                link: BUILD_URL, result: currentBuild.currentResult,
                title: JOB_NAME,
                webhookURL: "https://discord.com/api/webhooks/950640331618127933/BBv5vrZLc39Bs0IkXYXWSadLqryAHWOIvVEvkMeA25lO28vhoeccnNVlF2pPnVM_Yg0o"
            }
        }
    }
}