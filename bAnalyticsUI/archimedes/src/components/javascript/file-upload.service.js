import * as axios from 'axios'

const BASE_URL = 'http://10.4.3.17:3000'

function upload (formData) {
  const url = `${BASE_URL}/file_upload`
  return axios.post(url, formData)
}

const BASE_URL2 = 'http://localhost:3000'

function fileList () {
  const url = `${BASE_URL2}/hdfsFileList/a`
  return axios.get(url)
}

export { upload, fileList }
