pipeline {

    agent any
    environment {
      PASS = credentials("cbab8339-af2e-437c-8227-02e90033919e")

    }

    stages {

        stage('Build') {
            steps {
                sh '''
                    chown jenkins:jenkins mvnw
                    chmod a+x mvnw
                    ./mvnw -DskipTests clean package
                    # docker compose -f docker-compose-build.yaml build --no-cache
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
                           chown jenkins:jenkins mvnw
                           chmod a+x mvnw
                           docker run -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=books_store --name mysql-container mysql:5.7
                           ./mvnw -DskipTests clean package
                      '''
                }
                 post {
                        always {
                            sh 'docker stop mysql-container'
                            sh 'docker rm mysql-container'
                            junit 'java-repo/target/surefire-reports/*.xml'

                        }
                }
       }
   }
}
