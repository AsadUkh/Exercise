FROM sonatype/nexus3:3.19.1

ARG PLUGIN_VERSION=0.9.2
ARG BUNDLE_NAME=nexus-blobstore-google-cloud-${PLUGIN_VERSION}-bundle.kar
ARG KAR_URL=https://repository.sonatype.org/service/local/repositories/releases/content/org/sonatype/nexus/plugins/nexus-blobstore-google-cloud/${PLUGIN_VERSION}/${BUNDLE_NAME}
ADD --chown=nexus:nexus ${KAR_URL} /opt/sonatype/nexus/deploy
COPY --chown=nexus:nexus key.json /nexus-data/
RUN chmod 777 /nexus-data/key.json
#RUN chown nexus:nexus /nexus-data/key.json
ENV GOOGLE_APPLICATION_CREDENTIALS=/nexusdata/key.json
USER nexus
EXPOSE 8081
