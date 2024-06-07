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
        stage('Build Docker Image') {
            steps {
                script {
                   sh 'docker build --no-cache -t achat:v${BUILD_NUMBER} -f Dockerfile ./'
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script{
                    
                    sh 'echo docker2024 | docker login --username hibachemek --password-stdin'
                    sh 'docker tag achat:v${BUILD_NUMBER} hibachemek/achat:achat'
                    sh 'docker push hibachemek/achat:achat'
                }
            }
        }
        stage('Docker Compose') {
                     steps{
                         sh 'docker compose up -d'
                    }
                }


    }
}
