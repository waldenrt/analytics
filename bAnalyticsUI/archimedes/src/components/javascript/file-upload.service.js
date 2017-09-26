import * as axios from 'axios'

const BASE_URL = 'http://10.4.3.17:3000'

function upload (formData) {
  const url = `${BASE_URL}/file_upload`
  return axios.post(url, formData)
}

export { upload }
