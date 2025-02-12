#!/bin/bash

set -x

#make bucket
awslocal s3 mb s3://demo-s3
awslocal sqs create-queue --queue-name demo-queue

set -x