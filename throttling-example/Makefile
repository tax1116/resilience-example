IMAGE_NAME=throttling-example
PROFILE=docker
COMPOSE_FILE=../docker/throttling/docker-compose.yml

JAR_FILE=$(shell find build/libs -name '*.jar' | head -n 1)

build:
	./gradlew clean build

docker-build: check-jar
	docker build \
		--build-arg PROFILE=$(PROFILE) \
		-t $(IMAGE_NAME) .

check-jar:
	@test -f $(JAR_FILE) || (echo "JAR 파일이 없습니다: $(JAR_FILE)"; exit 1)

up:
	docker compose -f $(COMPOSE_FILE) up -d

down:
	docker compose -f $(COMPOSE_FILE) down --volumes --remove-orphans

clean:
	docker image rm -f $(IMAGE_NAME) || true
	docker builder prune -f

run: down clean build docker-build up