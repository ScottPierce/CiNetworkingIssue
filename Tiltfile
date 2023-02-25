docker_compose('./docker-compose.yml', project_name = 'adcentral-databases')
dc_resource('postgres', labels=["Database"])

local_resource(
  'migrate-job',
  resource_deps=['postgres'],
  labels=["Commands"],
  cmd='./gradlew :run',
  cmd_bat='gradlew :run',
)