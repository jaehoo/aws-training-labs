#!/bin/bash
export TOKEN=$(curl --request PUT "http://169.254.169.254/latest/api/token" --header "X-aws-ec2-metadata-token-ttl-seconds: 3600")

export AWS_REGION=$(curl -s http://169.254.169.254/latest/dynamic/instance-identity/document --header "X-aws-ec2-metadata-token: $TOKEN" | jq -r ".region")

#setting variables from secrets manager
export RDS_SECRET=$(aws secretsmanager get-secret-value --secret-id RDSSecret --region $AWS_REGION | jq --raw-output .SecretString)

$(jq -r 'to_entries | map("export "+.key + "=" + (.value | tostring)) | .[]' <<<"$RDS_SECRET")

java -jar /app/monolithic-java-webapp.jar --rds.dbinstance.host=jdbc:mysql://${host}:3306/LabRDSDB --rds.dbinstance.username=${username} --rds.dbinstance.password=${password} > /dev/null 2> /dev/null < /dev/null &