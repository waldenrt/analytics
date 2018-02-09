/**
 * Created by amerrill on 9/27/17.
 */

import * as axios from 'axios'

const BASE_URL = 'http://10.4.3.18:8001/api/v1/balor'

function cadence (jobId) {
  const url = `${BASE_URL}/balorcadence/${jobId}`
  return axios.get(url)
}

function summary (jobId) {
  const url = `${BASE_URL}/balorsummary/${jobId}`
  return axios.get(url)
}

function segment (jobId) {
  const url = `${BASE_URL}/balorsegment/${jobId}`
  return axios.get(url)
}

export { cadence, summary, segment }
