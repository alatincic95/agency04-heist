import axios from "axios";

const MEMBER_BASE_URL = `http://localhost:8080/member/`;
const CREATE_MEMBER_URL = `http://localhost:8080/create-member`;

class GetMemberService {
  getMember(memberId) {
    return axios.get(MEMBER_BASE_URL + `${memberId}`);
  }
  getMemberSkills(memberId) {
    return axios.get(MEMBER_BASE_URL + `${memberId}` + `/skills`);
  }
  updateMemberSkills1(memberId, memberSkills) {
    return axios.put(MEMBER_BASE_URL + `${memberId}` + `/skills`, memberSkills);
  }
  deleteMember(memberId) {
    return axios.delete(MEMBER_BASE_URL + `${memberId}`);
  }
  createMember1(member) {
    return axios.post(CREATE_MEMBER_URL, member);
  }
}

export default new GetMemberService();
