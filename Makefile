GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'
AWS_ECR_URL:=""

.PHONY: setup
.DEFAULT_GOAL := dockerize

dockerize:
	@rm -Rf target/
	@lein ring uberjar
	@mv target/*standalone.jar codcheck-api.jar
	@docker build -f docker/Dockerfile.production . -t fierycod/codcheck-api
	@docker tag fierycod/codcheck-api:latest $(AWS_ECR_URL)
	@docker push $(AWS_ECR_URL)
