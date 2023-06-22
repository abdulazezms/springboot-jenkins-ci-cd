
pipeline {

    agent any
    environment {
      PASS = credentials("cbab8339-af2e-437c-8227-02e90033919e")

    }
    stages {

        stage('Build') {
            steps {
                sh '''
                    cd books-manager
                    ./mvnw -DskipTests clean package
                    docker compose -f docker-compose-build.yaml build --no-cache
                   '''
            }

            post {
                 success {
                   archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
             }

        }

        stage('Test') {
            steps {
                sh '''
                    ./mvnw test
                   '''
            }

            post {
                always {
                  junit 'target/surefire-reports/*.xml'
                }
            }

        }

//         stage('Push') {
//             steps {
//                 sh 'jenkins/push/push.sh'
//             }
//         }
//
//         stage('Deploy') {
//             steps {
//                 sh 'jenkins/deploy/deploy.sh'
//
//             }
//         }
    }
}
