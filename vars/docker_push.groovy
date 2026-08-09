def call(Map config = [:]) {
    def rawImageName = config.imageName ?: error("Image name is required")
    def imageTag     = config.imageTag ?: 'latest'
    def credentials  = config.credentials ?: 'dockerHubCreds'

    withCredentials([usernamePassword(
        credentialsId: credentials,
        usernameVariable: 'DOCKER_USERNAME',
        passwordVariable: 'DOCKER_PASSWORD'
    )]) {
        // If config.username is provided, use it; otherwise fallback to $DOCKER_USERNAME from credentials
        def userPrefix = config.username ?: "\$DOCKER_USERNAME"
        def fullImageName = "${userPrefix}/${rawImageName}"

        echo "Pushing Docker image: ${fullImageName}:${imageTag}"

        sh """
            echo "\$DOCKER_PASSWORD" | docker login -u "\$DOCKER_USERNAME" --password-stdin
            docker push ${fullImageName}:${imageTag}
            docker push ${fullImageName}:latest
        """
    }
}
