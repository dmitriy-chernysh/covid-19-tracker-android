#! /bin/sh

#put release apk to dropbox
APK_FILES="${CIRCLE_WORKING_DIRECTORY}/apks/*-release.apk"
#DROPBOX_TOKEN="see ENV"

for FILE in $APK_FILES
do

  #check file is empty
  [ -s  "${FILE}" ]; echo "File ${FILE} is empty"; exit 1 \

  echo "Found APK: ${FILE}"

   FILE_NAME=$(basename "${FILE}")

   curl -X POST https://content.dropboxapi.com/2/files/upload \
    --header "Authorization: Bearer ${DROPBOX_TOKEN}" \
    --header "Dropbox-API-Arg: {\"path\": \"\/${FILE_NAME}\",\"mode\": \"overwrite\",\"autorename\": true,\"mute\": false}" \
    --header "Content-Type: application/octet-stream" \
    --data-binary @"${FILE}"
done