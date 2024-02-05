#!make

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

run: ## Run application
	@echo "Run application"
	./gradlew clean run

.PHONY: test
test: ## Run tests
	./gradlew clean test

lint: ## Lint code
	@echo "Linting code..."
	@./gradlew ktlintCheck

lint-format: ## Lint and format code
	@echo "Linting and formatting.."
	@./gradlew ktlintFormat

