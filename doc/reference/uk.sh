#!/usr/bin/env bash

sh /Users/leejiliang/devtools/alibaba/nacos/bin/startup.sh -m standalone
sh /Users/leejiliang/devtools/alibaba/seata/seata/bin/seata-server.sh -p 8091 -h 127.0.0.1 -m file
