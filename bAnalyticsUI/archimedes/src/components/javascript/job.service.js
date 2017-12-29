/**
 * Created by amerrill on 12/21/17.
 */

import * as axios from 'axios'

const BASE_URL = 'http://10.4.3.18:9003/api/v1/jobs/jobhistory'

function clientJobs (clientName) {
  const url = `${BASE_URL}/client/${clientName}`
  return axios.get(url)
}

function clientUserJobs (client, user) {
  const url = `${BASE_URL}/client/${client}/user/${user}`
  return axios.get(url)
}

function clientUserAppJobs (client, user, app) {
  const url = `${BASE_URL}/client/${client}/user/${user}/app/${app}`
  return axios.get(url)
}

function userJobs (user) {
  const url = `${BASE_URL}/user/${user}`
  return axios.get(url)
}

function appJobs (app) {
  const url = `${BASE_URL}/app/${app}`
  return axios.get(url)
}

function userAppJobs (user, app) {
  const url = `${BASE_URL}/user/${user}/app/${app}`
  return axios.get(url)
}

function clientAppJobs (client, app) {
  const url = `${BASE_URL}/client/${client}/app/${app}`
  return axios.get(url)
}

function addHistory (formData) {
  const url = `${BASE_URL}`
  return axios.post(url, formData)
}

export { clientJobs, clientUserJobs, clientUserAppJobs, userJobs, appJobs, userAppJobs, clientAppJobs, addHistory }
