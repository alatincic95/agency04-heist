import axios from "axios";

const HEISTS_BASE_URL = "http://localhost:8080/heist";

class HeistService {
  getHeists() {
    return axios.get(HEISTS_BASE_URL);
  }
  getHeist(heistId) {
    return axios.get(HEISTS_BASE_URL + `/` + `${heistId}`);
  }
  createHeist(heist) {
    return axios.post(HEISTS_BASE_URL, heist);
  }

  getHeistSkills(heistId) {
    return axios.get(HEISTS_BASE_URL + `${heistId}` + `/skills`);
  }

  deleteHeist(heistId) {
    return axios.delete(HEISTS_BASE_URL + `/` + `${heistId}`);
  }
  updateHeistSkills(heistId, skills) {
    return axios.put(HEISTS_BASE_URL + `${heistId}` + `/skills`, skills);
  }
}

export default new HeistService();
