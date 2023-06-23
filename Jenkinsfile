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
                            docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=books_store --name mysql-container -v /db_data:/var/lib/mysql mysql:5.7
                            sleep 15
                            export DATABASE_HOST=jdbc:mysql://192.168.100.117:3306/books_store
                            export DATABASE_PASSWORD=1234
                           ./mvnw test
                      '''
                }
                 post {
                        always {
                            sh 'docker stop mysql-container'
                            sh 'docker rm mysql-container'
                            junit 'target/surefire-reports/*.xml'

                        }
                }
       }
   }
}
