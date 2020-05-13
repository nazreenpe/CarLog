import React from "react";
import Files from "react-butterfiles";

import handleExpiredSession from './ExpiredSessionHandler';
import { typeCastExpression } from "@babel/types";

const getPresignedPostData = selectedFile => {
  return fetch("/api/s3upload", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json"
    }
  })
    .then(handleExpiredSession)
    .then(res => {
      if (!res.ok) {
        throw new Error()
      }
      return res.json()
    })
    .catch(error => {
      console.log("Error fetching signed URL")
    })
};


const uploadFileToS3 = (presignedUrl, key, file, callback) => {
  return fetch(presignedUrl, {
    method: "PUT",
    body: file
  })
    .then(res => {
      if (!res.ok) {
        throw new Error()
      }
      return res
    })
    .then(res => {
      callback(key, file.name)
    })
    .catch(error => {
      console.log("Error uploading file")
    })
};

class FileUploadButton extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      onUpload: props.onUpload
    }
  }
  render() {
    return (
      <Files
        onSuccess={async ([selectedFile]) => {
          const signedData = await getPresignedPostData(selectedFile);
    
          try {
            const { file } = selectedFile.src;
            await uploadFileToS3(signedData.url, signedData.key, file, this.state.onUpload);
            console.log("File was successfully uploaded!");
          } catch (e) {
            console.log("An error occurred!", e.message);
          }
        }}
      >
        {({ browseFiles }) => <button onClick={browseFiles}>Select file...</button>}
      </Files>
    )
  }
}

export default FileUploadButton