pipeline {
    agent any
    stages {
        stage('Checkout git') {
            steps {
                echo 'Pulling...'
                git branch: 'Gaith-branch', url: 'https://github.com/Gaithb/achat.git'
            }
        }
        stage('Testing Maven') {
            steps {
                sh 'mvn -version'
            }
        }
    }
}
