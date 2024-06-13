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
                        sh "mvn sonar:sonar -Dsonar.host.url=http://192.168.50.4:9000 -Dsonar.login=admin -Dsonar.password=Simba3420."
                    }
                }
           stage('Mockito') {
                       steps {
                           sh 'mvn test'
                       }
                   }


    }
}