
#  Simple CI/CD with Jenkins




__Build Stage__

This stage builds the project by running Maven and packaging the application. The resulting JAR file is archived as an artifact.


__Test Stage__

In this stage, a temporary MySQL container is started for running application tests. The database URL and password are exported as environment variables for the application to use. The tests are executed using Maven. After the tests complete, the MySQL container is stopped and removed, and test reports are collected.


__Push Stage__

In this stage, the Docker image for the application is built using the provided Dockerfile. The image is tagged, logged in to the Docker registry using Jenkins global credentials, and pushed to the registry.


__Deploy Stage__

The final stage involves deploying the application to a remote machine. The necessary image name, tag, and password are stored locally and securely copied to the remote machine. A file triggering the deployment process is also copied. Finally, the deployment is executed on the remote machine using Docker Compose, by sshing into it and execute the necessary command. A copy of the docker compose used in the remote machine exists in this repo.

__Note__

The mechanism used for passing credentials in this pipeline, such as storing them in a file and transferring them to the remote machine, is not considered a secure approach, even though I am using ssh protocol in the process, because those credentials are stored in plaintext in both the jenkins agent as well as the remote server. It is recommended to utilize the secure credential management services provided by cloud providers, which allow for secure sharing of credentials between machines.
Also, in this example, I used docker compose, but one can easily use a remote k8s cluster, to apply the deployment and use different strategies upon pushes to the development/testing/production branch.







