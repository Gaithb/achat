stage('Build Docker Image') {
            steps {
                script {
                   sh 'docker build --no-cache -t achat:v${BUILD_NUMBER} -f Dockerfile ./'
                }
            }
        }
