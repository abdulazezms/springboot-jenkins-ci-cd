
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
                // Start MySQL container
                script {
                    docker.image('mysql:5.7').withRun('-p 3306:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=books_store')
                    { container ->
                        // Wait for the MySQL container to start
                        sh 'sleep 10'

                        // Run your tests here, making sure to set the environment variables
                        sh 'export DATABASE_HOST=jdbc:mysql://localhost:3306/books_store'
                        sh 'export DATABASE_USERNAME=root'
                        sh 'export DATABASE_PASSWORD=1234'

                        // Run your application's jar file here
                        sh 'java -jar your-application.jar'
                    }
                }
            }
        }
//         stage('Test') {
//             steps {
//                 sh '''
//                     ./mvnw test
//                    '''
//             }
//
//             post {
//                 always {
//                   junit 'target/surefire-reports/*.xml'
//                 }
//             }
//
//         }
}
// //         stage('Push') {
// //             steps {
// //                 sh 'jenkins/push/push.sh'
// //             }
// //         }
// //
// //         stage('Deploy') {
// //             steps {
// //                 sh 'jenkins/deploy/deploy.sh'
// //
// //             }
// //         }
//     }
// }
