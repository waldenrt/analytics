/**
 * Created by amerrill on 10/19/17.
 */

import * as axios from 'axios'

const BASE_URL = 'http://10.4.3.18:9000/api/v1/lifecycle'

function summary (jobId) {
  const url = `${BASE_URL}/lifecycleresults/${jobId}`
  return axios.get(url)
}

function product (jobId) {
  const url = `${BASE_URL}/lifecycleprodresults/${jobId}`
  return axios.get(url)
}

function migration (jobId) {
  const url = `${BASE_URL}/migrationresults/${jobId}`
  return axios.get(url)
}

export { product, summary, migration }
