#!/bin/bash

if docker network create resilience-example; then
  echo "Docker 네트워크 'resilience-example' 성공적으로 생성되었습니다."
else
  echo "Docker 네트워크 생성 중 오류가 발생했습니다."
fi