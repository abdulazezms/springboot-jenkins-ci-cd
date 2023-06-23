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
                            docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=books_store --name mysql-container -v /db_data_test:/var/lib/mysql mysql:5.7
                            sleep 15
                            export DATABASE_HOST=jdbc:mysql://192.168.100.117:3306/books_store
                            export DATABASE_PASSWORD=1234
                           ./mvnw test
                            unset DATABASE_HOST
                            unset DATABASE_PASSWORD

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

       stage('Push') {

            steps{
                sh '''

                export IMAGE=books-manager

                echo "*** building the image ***"
                docker build -t $IMAGE:$BUILD_TAG .

                echo "** Logging in ***"
                docker login -u atechy -p $PASS

                echo "*** Tagging image ***"
                docker tag $IMAGE:$BUILD_TAG atechy/$IMAGE:$BUILD_TAG

                echo "*** Pushing image ***"
                docker push atechy/$IMAGE:$BUILD_TAG

                '''

            }

       }


      stage('Deploy') {

           steps{
               sh '''


               echo "*** Storing image, tag, and password locally ***"

               echo "books-manager" > /tmp/books-manager-cred
               echo $BUILD_TAG >> /tmp/books-manager-cred
               echo $PASS >> /tmp/books-manager-cred

               echo "*** Secure copy file to remote machine at prod-user@192.168.100.8:/tmp/books-manager-cred"
               scp -i /opt/prod /tmp/books-manager-cred prod-user@192.168.100.8:/tmp/books-manager-cred

               echo "Creating the file containing necessary exports to export environment variables in the remote machine"

               echo "*** Secure copy file that triggers the docker compose on the remote machine"
               scp -i /opt/prod publish.sh prod-user@192.168.100.8:/tmp/books-manager-publish.sh

               echo "*** Execute the file that triggers the docker compose on the remote machine ***"
               ssh -i /opt/prod prod-user@192.168.100.8 "chmod a+x /tmp/books-manager-publish.sh && /tmp/books-manager-publish.sh"



               '''

           }

      }
   }
}
