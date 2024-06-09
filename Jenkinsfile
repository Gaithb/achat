pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    enbvironment {
        SCANNER_HOME = tool 'sonar-scanner'
    }

    stages {
        stage('Git checkout') {
            steps {
                git branch: 'stock-module-faresbouzayen', credentialsId: 'git-cred', url: 'https://github.com/Gaithb/achat.git'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('File system scan') {
            steps {
                sh 'trivy fs --format table -o trivy-fs-report.html .'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                 withSonarQubeEnv('sonar') {
                     sh '''
                          $SCANNER_HOME/bin/sonar-scanner \
                          -Dsonar.projectName=achat-devops \
                          -Dsonar.projectKey=achat-devops \
                          -Dsonar.java.binaries=.
                    '''
            }
        }
    }
        stage('Quality Gate') {
            steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                }
            }
        }
        stage('Build') {
            steps {
                echo 'mvn package'
            }
        }
        stage('Publish to Nexus') {
            steps {
                withMaven(globalMavenSettingsConfig: 'global-settings', jdk: 'jdk17', maven: 'maven3', mavenSettingsConfig: '', traceability: true) {
                    sh 'mvn deploy'
                }
            }
        }
        stage('Build & tag Docker') {
            steps {
                withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                    sh "docker build -t ferisbouzayen/achat-devops:latest ."
                }
            }
        }
        stage('Docker Image scan') {
            steps {
                sh 'trivy image --format table -o trivy-fs-report.html ferisbouzayen/achat-devops:latest'
            }
        }
        stage('Push Docker Image') {
            steps {
                withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                    sh "docker push -t ferisbouzayen/achat-devops:latest"
                }
            }
        }
    }
}
