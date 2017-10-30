/**
 * Created by amerrill on 10/30/17.
 */

import * as axios from 'axios'

const BASE_URL = 'http://10.4.3.18:9002/api/v1/Quantile'

function quantMig (jobId) {
  const url = `${BASE_URL}/quantilemigration/${jobId}`
  return axios.get(url)
}

function quantSum (jobId) {
  const url = `${BASE_URL}/quantilesummary/${jobId}`
  return axios.get(url)
}

function quantProd (jobId) {
  const url = `${BASE_URL}/quantileproduct/${jobId}`
  return axios.get(url)
}

export { quantMig, quantSum, quantProd }
