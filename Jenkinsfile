pipeline {
    agent any

    stages {
        stage('mvn_clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('mvn_compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                sh "mvn sonar:sonar -Dsonar.host.url=http://192.168.50.4:9000 -Dsonar.login=admin -Dsonar.password=sonar"

            }
        }
        stage('Mokito') {
                    steps {
                        sh 'mvn test'
                    }
                }
        stage('Nexus') {
                    steps {
                        sh 'mvn deploy'
                     }
                }


    }
}
