import axios from "axios";

const MEMBERS_BASE_URL = "http://localhost:8080/members";

export default class MemberService {
  static getMembers() {
    return axios.get(MEMBERS_BASE_URL);
  }
}
