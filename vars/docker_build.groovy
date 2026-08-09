def call(Map config = [:]) {
    def rawImageName = config.imageName ?: error("Image name is required")
    def imageTag     = config.imageTag ?: 'latest'
    def dockerfile   = config.dockerfile ?: 'Dockerfile'
    def context      = config.context ?: '.'
    def username     = config.username ? "${config.username}/" : ""

    // Combines username + imageName (e.g., cshubham1727/two-tier-flask-app)
    def fullImageName = "${username}${rawImageName}"

    echo "Building Docker image: ${fullImageName}:${imageTag} using ${dockerfile}"

    sh """
        docker build -t ${fullImageName}:${imageTag} -t ${fullImageName}:latest -f ${dockerfile} ${context}
    """
}
