#!/bin/sh

# sudo yum install jq -y

# Writting launch.json to .c9 dir
echo '{
    "configurations": [
        {
            "type": "aws-sam",
            "request": "direct-invoke",
            "name": "API lambda-python3.8:getAllProducts (python3.8)",
            "invokeTarget": {
                "target": "api",
                "templatePath": "Lab4CodeCommitRepo/lambdas/template.yml",
                "logicalId": "getAllProducts"
            },
            "api": {
                "path": "/api/products/query",
                "httpMethod": "get",
                "payload": {
                    "json": {}
                }
            },
            "lambda": {
                "runtime": "python3.8"
            }
        },
        {
            "type": "aws-sam",
            "request": "direct-invoke",
            "name": "lambda-python3.8:getAllProducts (python3.8)",
            "invokeTarget": {
                "target": "template",
                "templatePath": "Lab4CodeCommitRepo/lambdas/template.yml",
                "logicalId": "getAllProducts"
            },
            "lambda": {
                "payload": {},
                "environmentVariables": {},
                "runtime": "python3.8"
            }
        }
    ]
}' > ../.c9/launch.json

echo 'The environment setup finished successfully!'