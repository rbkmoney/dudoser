FROM dr.rbkmoney.com/rbkmoney/service-java:@dockerfile.base.service.tag@
MAINTAINER Anatoly Cherkasov <a.cherkasov@rbkmoney.com>

COPY @artifactId@-@version@.jar /opt/@artifactId@/@artifactId@.jar
COPY containerpilot.json /etc/containerpilot.json

ENTRYPOINT ["/bin/containerpilot", "-config", "file:///etc/containerpilot.json",  "java"]
CMD ["-jar","/opt/@artifactId@/@artifactId@.jar"]

EXPOSE @server.port@

LABEL com.rbkmoney.@artifactId@.parent=service-java \
    com.rbkmoney.@artifactId@.parent_tag=@dockerfile.base.service.tag@ \
    com.rbkmoney.@artifactId@.build_img=build \
    com.rbkmoney.@artifactId@.build_img_tag=@dockerfile.build.container.tag@ \
    com.rbkmoney.@artifactId@.commit_id=@git.revision@ \
    com.rbkmoney.@artifactId@.commit_number=@git.commitsCount@ \
    com.rbkmoney.@artifactId@.branch=@git.branch@
WORKDIR /opt/@artifactId@
