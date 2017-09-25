import * as axios from 'axios'

const BASE_URL = 'http://localhost:3000'

function upload (formData) {
  const url = `${BASE_URL}/file_upload`
  return axios.post(url, formData)
}

export { upload }
