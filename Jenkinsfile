
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
                           docker run mysql:5.7 --name mysql-container -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=books_store
                           ./mvnw -DskipTests clean package
                      '''
                }
                 post {
                        always {
                            // Stop and remove the MySQL container
                            sh 'docker stop mysql-container'
                            sh 'docker rm mysql-container'
                        }
                    }
       }
}
