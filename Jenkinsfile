pipeline {
    agent any

    environment {
        MAIN_VERSION = "1.9"
        BUILD_VERSION = "${MAIN_VERSION}-b${env.BUILD_NUMBER}"
        DOCKER_CREDENTIALS = credentials('5a994b69-2de9-4b5c-a541-1f9495092a2a')
    }

    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Checkout GIT') {
            steps {
                git branch: 'Gaith-b',
                url: 'https://github.com/Gaithb/achat.git'
            }
        }

        stage('Junit|Mockito') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }       

        stage('Run Tests with JaCoCo') {
            steps {
                script {
                    // Run tests with JaCoCo coverage
                    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent test'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh 'sudo chmod 666 /var/run/docker.sock'
                sh 'docker start d45da163b99d'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
            }
        }
        
        stage('Install') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'docker start e6f0b273dff8'
                echo 'Deploying to Nexus server'
                sh 'mvn deploy'
            }
        }
        
stage('Docker Build') {
    steps {
        script {
            def jarName = "achat-${BUILD_VERSION}.jar"
            sh "cp target/achat-1.9.jar target/${jarName}"  // Assurez-vous que le fichier JAR a le nom correct
            sh "docker build -t docker.io/gaihdocker/achat:${BUILD_VERSION} ."
        }
    }
}


        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: '5a994b69-2de9-4b5c-a541-1f9495092a2a', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        sh "echo ${DOCKERHUB_PASSWORD} | docker login -u ${DOCKERHUB_USERNAME} --password-stdin"
                        sh "docker push docker.io/gaihdocker/achat:${BUILD_VERSION}"
                    }
                }
            }
        }


        stage('Docker Compose UP') {
            steps {
                script {
                    sh 'docker-compose down'
                    sh 'docker-compose up -d'
                }
            }
        }

        stage('Grafana') {
            steps {
                sh 'docker start 8922def84c37'
            }
        }
    }
    
    post {
        success {
            mail bcc: '',
                 body: """ Hello,

Your project to purchase or validate the module, DevOps class has been successful. Congratulations on your excellent work.

Sincerely,  
                            URL-${env.BUILD_NUMBER} : "${env.BUILD_URL}"
                 """,
                 cc: '',
                 from: '',
                 replyTo: '',
                 subject: "Pipeline Passed  - Build Number : ${env.BUILD_NUMBER}",
                 to: 'mohamedgaith.basly@esprit.tn'
        }
        failure {
            mail bcc: '',
                 body: """ Hello,

Your project to purchase or validate the module, DevOps class has failed. Please review it and find a solution to resolve this issue.

Thank you for your understanding. : 
                            URL-${env.BUILD_NUMBER} : "${env.BUILD_URL}"
                 """,
                 cc: '',
                 from: '',
                 replyTo: '',
                 subject: "Pipeline Failed - Build Num : ${env.BUILD_NUMBER}",
                 to: 'mohamedgaith.basly@esprit.tn'
        }
    }
}
