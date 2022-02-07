package com.anteag04.springbootbackend.controller;

import com.anteag04.springbootbackend.customProperties.ApplicationProperties;
import com.anteag04.springbootbackend.exception.ResourceNotFoundException;
import com.anteag04.springbootbackend.helper.*;
import com.anteag04.springbootbackend.model.*;
import com.anteag04.springbootbackend.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private MemberSkillRepository memberSkillRepository;

    @Autowired
    private SkillsRefactoredRepository skillsRefactoredRepository;

    @Autowired
    private HeistRepository heistRepository;

    @Autowired
    private HeistSkillRepository heistSkillRepository;
    @Autowired
    private SkillRepositoryHeist skillRepositoryHeist;
    @Autowired
    private SkillsRefactoredRepositoryHeist skillsRefactoredRepositoryHeist;

    @Autowired
    private HeistMembersRepository heistMembersRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    // get all members REST API
    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // create member
    @PostMapping("/create-member")
    public ResponseEntity<String> createMember(@RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper();
        boolean write2Database = false;
        String name = "";
        try {
            JsonNode actualObj = mapper.readTree(request);
            name = actualObj.path("name").asText();
            String email = actualObj.path("email").asText();
            String sex = actualObj.path("sex").asText().toUpperCase();
            String mainSkill = actualObj.path("mainSkill").asText();
            String status = actualObj.path("status").asText().toUpperCase();

            MemberSkill memberSkill = new MemberSkill();

            Long skillId;

            String skillName = "";

            Skill skill = skillRepository.findByName(skillName);

            MemberSkillKey memberSkillKey = new MemberSkillKey();

            String level = "";

            Member member = memberRepository.findByEmail(email);
            // Provjera postoji li vec taj mail u bazi
            List<Member> allMembersForEmail = memberRepository.findAll();
            String[] allMails = new String[allMembersForEmail.size()];
            int counter = 0;
            for (Member memberForEmail : allMembersForEmail){
                allMails[counter] = memberForEmail.getEmail();
                counter++;
            }

            for (int a = 0; a < allMails.length; a++){
                if (allMails[a] == email){
                    return new ResponseEntity<>("There is already member with that email!", HttpStatus.METHOD_NOT_ALLOWED);
                }
            }


            if (member == null) {
                member = new Member();
                member.setName(name);
                member.setEmail(email);
                member.setSex(sex);
                member.setMainSkill(mainSkill);
                member.setStatus(status);
                memberRepository.save(member);
                write2Database = true;
            }



            Long memberId = member.getId();


            JsonNode arrNode = actualObj.path("skills");

            if (arrNode.isArray()) {
                for (JsonNode objNode : arrNode) {
                    skillName = objNode.get("name").asText();
                    level = objNode.get("level").asText();



                    char[] checkLevel = level.toCharArray();
                    for (int i = 0; i < level.length(); i++){
                        if(checkLevel[i] != '*' || checkLevel.length == 0 || checkLevel.length > 10){
                            return new ResponseEntity<String>("Provide valid level", HttpStatus.BAD_REQUEST);
                        }
                    }

                    if (skill == null) {
                        skill = new Skill();
                        skill.setName(skillName);
                        skillId = skill.getId();
                        skillRepository.save(skill);
                        write2Database = true;
                    }

                    skillId = skill.getId();
                    memberSkillKey.setMemberId(memberId);
                    memberSkillKey.setSkillId(skillId);

                    if (memberSkillKey == null) {
                        memberSkillKey.setMemberId(memberId);
                        memberSkillKey.setSkillId(skillId);
                    }


                    if (memberSkill == null) {
                        memberSkill.setId(memberSkillKey);
                        memberSkill.setLevel(level);
                        memberSkillRepository.save(memberSkill);

                    }

                    SkillsRefactored skillsRefactored = new SkillsRefactored();
                    if (member.getId() == memberSkillKey.getMemberId() && skill.getId() == memberSkillKey.getSkillId()) {

                        skillsRefactored.setIDMember(memberId);
                        skillsRefactored.setSkillName(skillName);
                        skillsRefactored.setSkillLevel(level);
                        skillsRefactoredRepository.save(skillsRefactored);
                    }
                    ;
                    // Provjera main skilla
                    int k = 0;
                    List<SkillsRefactored> allSkills = skillsRefactoredRepository.findByIDMember(member.getId());
                    String[] allSkillNames = new String[allSkills.size()];
                    for (SkillsRefactored skill1 : allSkills){
                        allSkillNames[k] = skill1.getSkillName() ;
                        k++;
                    }
                    int m = 0;
                    for(int t = 0; t < 1; t++){
                        for (int n = 0; n < allSkillNames.length; n++){
                            System.out.println(member.getMainSkill());
                            System.out.println(allSkillNames[n]);
                            if (!member.getMainSkill().contentEquals(allSkillNames[n])){
                                m++;
                            }
                        }
                    }
                    if (m == allSkillNames.length){
                        return new ResponseEntity<>("Not valid main skill", HttpStatus.BAD_REQUEST);
                    }else if(mainSkill != null){
                        member.setMainSkill(mainSkill);
                    }

                    if (!member.getSex().contentEquals("F") && !member.getSex().contentEquals("M")){
                        return new ResponseEntity<>("Please enter valid sex", HttpStatus.BAD_REQUEST);
                    }



                }

                sendEmail(member.getEmail(),"Hey " + member.getName(), "Kako si");




            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (write2Database)
            return new ResponseEntity<String>("Zapisao u bazu " + name, HttpStatus.OK);

        return new ResponseEntity<String>("Zapis za " + name + " vec postoji", HttpStatus.BAD_REQUEST);
    }
    // get member by id
    @GetMapping("/member/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) {

        Member member = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No member with id " + id));
        return ResponseEntity.ok(member);

    }

    public void sendEmail( String to, String subject, String content){
        String from = "professor@gmail.com";
        String host = "localhost";

        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject(subject);
            // Now set the actual message
            message.setText(content);
            // Send message
            Transport.send(message);
            System.out.println("Message sent successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    @DeleteMapping("member/{id}")
    public String deleteMemberById(@PathVariable("id") Long id){
        memberRepository.deleteById(id);
        return ("Member with id:" + id + "deleted");
    }

    // get member skills
    @GetMapping("member/{id}/skills")
    public List<SkillsRefactored> getMemberSkills(@PathVariable("id") Long id) {
        return skillsRefactoredRepository.findByIDMember(id);

    }

    // delete member skill
    @DeleteMapping("/member/{id}/skills/{skill_name}")
    public void deleteMemberSkill(@PathVariable("id") Long id, @PathVariable("skill_name") String skillName) {
        List<SkillsRefactored> deletion = skillsRefactoredRepository.findByIDMember(id);
        for (SkillsRefactored skill1 : deletion) {
            if (skill1.getSkillName().contains(skillName)) {
                Long deletionId = skill1.getId();
                skillsRefactoredRepository.deleteById(deletionId);
            }
        }
    }

    // update member skills
    @PutMapping ("/member/{member_id}/skills")
    public  String updateMemberSkills(@RequestBody String request, @PathVariable("member_id") Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(request);
        JsonNode arrNode = actualObj.path("skills");

        MemberSkill memberSkill = new MemberSkill();

        Long skillId;

        String skillName = "";

        Skill skill = skillRepository.findByName(skillName);

        MemberSkillKey memberSkillKey = new MemberSkillKey();

        String level = "";


        Member member = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No member with id " + id));

        Long memberId = member.getId();

        String mainSkill = actualObj.path("mainSkill").asText();
        member.setMainSkill(mainSkill);

        List<SkillsRefactored> list = skillsRefactoredRepository.findByIDMember(id);

        for (SkillsRefactored skill1 : list) {

            if (skill1.getIDMember() == id) {
                Long deletionId = skill1.getId();
                skillsRefactoredRepository.deleteById(deletionId);
            }
        }



            if (arrNode.isArray()) {
                for (JsonNode objNode : arrNode) {
                    skillName = objNode.get("name").asText();
                    level = objNode.get("level").asText();


                    if (skill == null) {
                        skill = new Skill();
                        skill.setName(skillName);
                        skillId = skill.getId();
                        skillRepository.save(skill);
                    }

                    skillId = skill.getId();
                    memberSkillKey.setMemberId(memberId);
                    memberSkillKey.setSkillId(skillId);

                    if (memberSkillKey == null) {
                        memberSkillKey.setMemberId(memberId);
                        memberSkillKey.setSkillId(skillId);
                    }


                    if (memberSkill == null) {
                        memberSkill.setId(memberSkillKey);
                        memberSkill.setLevel(level);
                        memberSkillRepository.save(memberSkill);

                    }

                    SkillsRefactored skillsRefactored = new SkillsRefactored();
                    if (member.getId() == memberSkillKey.getMemberId() && skill.getId() == memberSkillKey.getSkillId()) {

                        skillsRefactored.setIDMember(memberId);
                        skillsRefactored.setSkillName(skillName);
                        skillsRefactored.setSkillLevel(level);
                        skillsRefactoredRepository.save(skillsRefactored);
                    }
                    ;
                }


            }


    return "Skills updated";
    }

    // create heist
    @PostMapping("/heist")
    public String createHeist(@RequestBody String request) {

        ObjectMapper mapper = new ObjectMapper();
        boolean write2Database = false;
        String name = "";
        try {
            JsonNode actualObj = mapper.readTree(request);
            name = actualObj.path("name").asText();
            String location = actualObj.path("location").asText();
            String startTime = actualObj.path("startTime").asText();
            String endTime = actualObj.path("endTime").asText();

            HeistSkill heistSkill = new HeistSkill();

            Long skillId;

            String skillName = "";

            Skill skill = skillRepository.findByName(skillName);

            HeistSkillKey heistSkillKey = new HeistSkillKey();

            String level = "";

            String members = "";



            Heist heist = heistRepository.findByName(name);
            if (heist == null) {
                heist = new Heist();
                heist.setName(name);
                heist.setLocation(location);
                heist.setStartTime(startTime);
                heist.setEndTime(endTime);
                heistRepository.save(heist);
                write2Database = true;
            }

            Long heistId = heist.getId();

            System.out.println(endTime);
            JsonNode arrNode = actualObj.path("skills");

            if (arrNode.isArray()) {
                for (JsonNode objNode : arrNode) {
                    skillName = objNode.get("name").asText();
                    level = objNode.get("level").asText();
                    members = objNode.get("members").asText();


                    if (skill == null) {
                        skill = new Skill();
                        skill.setName(skillName);
                        skillId = skill.getId();
                        skillRepository.save(skill);
                        write2Database = true;
                    }

                    skillId = skill.getId();
                    heistSkillKey.setHeistId(heistId);
                    heistSkillKey.setSkillId(skillId);

                    if (heistSkillKey == null) {
                        heistSkillKey.setHeistId(heistId);
                        heistSkillKey.setSkillId(skillId);
                    }


                    if (heistSkill == null) {
                        heistSkill.setId(heistSkillKey);
                        heistSkill.setLevel(level);
                        heistSkill.setMembers(members);
                        heistSkillRepository.save(heistSkill);

                    }

                    SkillsRefactoredHeist skillsRefactoredHeist = new SkillsRefactoredHeist();
                    if (heist.getId() == heistSkillKey.getHeistId() && skill.getId() == heistSkillKey.getSkillId()) {

                        skillsRefactoredHeist.setIDHeist(heistId);
                        skillsRefactoredHeist.setSkillName(skillName);
                        skillsRefactoredHeist.setSkillLevel(level);
                        skillsRefactoredHeist.setMembers(members);
                        skillsRefactoredRepositoryHeist.save(skillsRefactoredHeist);
                        //skillsRefactoredRepository.save(skillsRefactored);
                    }
                    ;
                }
            startingAndFinishingHeistAutomatically(heistId);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (write2Database)
            return "Zapisao u bazu " + name;

        return "Zapis za " + name + " vec postoji";

    }

    @GetMapping("/heist")
    public List<Heist> getAllHeists(){
        return heistRepository.findAll();
    }

    @PutMapping ("/heist/{heist_id}/skills")
    public  void updateHeistSkills(@RequestBody String request, @PathVariable("heist_id") Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(request);
        JsonNode arrNode = actualObj.path("skills");

        HeistSkill heistSkill = new HeistSkill();

        Long skillId;

        String skillName = "";

        Skill skill = skillRepository.findByName(skillName);

        HeistSkillKey heistSkillKey = new HeistSkillKey();

        String level = "";

        String members = "";


        Heist heist = heistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No heist with id " + id));

        Long heistId = heist.getId();
        List<SkillsRefactoredHeist> list = skillsRefactoredRepositoryHeist.findByIDHeist(id);

        for (SkillsRefactoredHeist skill1 : list) {

            if (skill1.getIDHeist() == id) {
                Long deletionId = skill1.getId();
                skillsRefactoredRepositoryHeist.deleteById(deletionId);
            }
        }



        if (arrNode.isArray()) {
            for (JsonNode objNode : arrNode) {
                skillName = objNode.get("name").asText();
                level = objNode.get("level").asText();
                members = objNode.get("members").asText();


                if (skill == null) {
                    skill = new Skill();
                    skill.setName(skillName);
                    skillId = skill.getId();
                    skillRepository.save(skill);
                }

                skillId = skill.getId();
                heistSkillKey.setHeistId(heistId);
                heistSkillKey.setSkillId(skillId);


                if (heistSkillKey == null) {
                    heistSkillKey.setHeistId(heistId);
                    heistSkillKey.setSkillId(skillId);
                }


                if (heistSkill == null) {
                    heistSkill.setId(heistSkillKey);
                    heistSkill.setLevel(level);
                    heistSkillRepository.save(heistSkill);

                }

                SkillsRefactoredHeist skillsRefactoredHeist = new SkillsRefactoredHeist();
                if (heist.getId() == heistSkillKey.getHeistId() && skill.getId() == heistSkillKey.getSkillId()) {

                    skillsRefactoredHeist.setIDHeist(heistId);
                    skillsRefactoredHeist.setSkillName(skillName);
                    skillsRefactoredHeist.setSkillLevel(level);
                    skillsRefactoredHeist.setMembers(members);
                    skillsRefactoredRepositoryHeist.save(skillsRefactoredHeist);
                }
                ;
            }


        }



    }

    @GetMapping("/heist/{heist_id}")
    public HeistWithSkills getHeistById(@PathVariable("heist_id") Long id){
        Heist heist = new Heist();

        heist = heistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No heist with id " + id));

        List<SkillsRefactoredHeist> heistSkills = getHeistSkills(id);
        HeistWithSkills heistWithSkills = new HeistWithSkills();
        SkillHelperHeist[] heistSkillHelper = new SkillHelperHeist[heistSkills.size()];

        heistWithSkills.setId(heist.getId());
        heistWithSkills.setName(heist.getName()) ;
        heistWithSkills.setLocation(heist.getLocation());
        heistWithSkills.setStartTime(heist.getStartTime());
        heistWithSkills.setEndTime(heist.getEndTime());
        heistWithSkills.setSkills(heistSkills);
        heistWithSkills.setMembers(heist.getMembers());
        heistWithSkills.setStatus(heist.getStatus());


        return heistWithSkills;
    }

    @DeleteMapping("/heist/{id}")
    public String deleteHeistById(@PathVariable("id") Long id){
        heistRepository.deleteById(id);
        return ("Heist with id " + id + "deleted");
    }

    @GetMapping("/heist/{heist_id}/skills")
    public List<SkillsRefactoredHeist> getHeistSkills(@PathVariable("heist_id") Long id){
       return skillsRefactoredRepositoryHeist.findByIDHeist(id);
    }


    // get eligible members for heist
    @GetMapping("/heist/{heist_id}/eligible_members")
    public MemberWithSkills[] getEligibleMembers(@PathVariable("heist_id") Long id){

        List<SkillsRefactored> allMembers = skillsRefactoredRepository.findAll();
        List<SkillsRefactoredHeist> heistSkills = skillsRefactoredRepositoryHeist.findByIDHeist(id);
        SkillHelperHeist[] heistSkillHelper = new SkillHelperHeist[heistSkills.size()];
        SkillHelperMembers[] memberSkillHelper = new SkillHelperMembers[allMembers.size()];


       int i = 0;
            for (SkillsRefactoredHeist skill : heistSkills){
                heistSkillHelper[i] = new SkillHelperHeist();
                heistSkillHelper[i].setName(skill.getSkillName()) ;
                heistSkillHelper[i].setLevel(skill.getSkillLevel());
                i++;
            }


        int j = 0;
        for (SkillsRefactored skill1 : allMembers){
            memberSkillHelper[j] = new SkillHelperMembers();
            memberSkillHelper[j].setMemberId(skill1.getIDMember());
            memberSkillHelper[j].setName(skill1.getSkillName()) ;
            memberSkillHelper[j].setLevel(skill1.getSkillLevel());
            j++;
        }

        Set<Long> eligibleMembersId = new HashSet<>();


            for(int l = 0; l < heistSkillHelper.length; l++) {
                for (int k = 0; k < memberSkillHelper.length; k++) {
                    if (heistSkillHelper[l].getName().contentEquals(memberSkillHelper[k].getName()) && memberSkillHelper[k].getLevel().length() > heistSkillHelper[l].getLevel().length()) {

                        eligibleMembersId.add(memberSkillHelper[k].getMemberId());

                    }
                }
            }


        Long[] eligibles = new Long[eligibleMembersId.size()];
        eligibleMembersId.toArray(eligibles);

        Member[] members = new Member[eligibles.length];
        MemberWithSkills[] membersWithSkills = new MemberWithSkills[eligibles.length];



        for (int a = 0; a < eligibles.length; a++) {
            Member member = new Member();
            MemberWithSkills memberWithSkills = new MemberWithSkills();
            member = memberRepository.getById(eligibles[a]);
            members[a] = member;
            memberWithSkills.setName(member.getName());
            memberWithSkills.setSex(member.getSex());
            memberWithSkills.setEmail(member.getEmail());
            memberWithSkills.setMainSkill(member.getMainSkill());
            memberWithSkills.setSkils(skillsRefactoredRepository.findByIDMember(id));
            memberWithSkills.setStatus(member.getStatus());

            if(memberWithSkills.getStatus().contentEquals("AVAILABLE") ||  memberWithSkills.getStatus().contentEquals("RETIRED")) {
                membersWithSkills[a] = memberWithSkills;
            }
        }




        return membersWithSkills;
    }

    // Confirm members of a heist
    @PutMapping("/heist/{heist_id}/members")
    public ResponseEntity<String[]> confirmHeistMembers(@PathVariable("heist_id") Long id, @RequestBody String request){

        ObjectMapper mapper = new ObjectMapper();
        HeistWithSkills heist = new HeistWithSkills();
        try {

            MemberWithSkills[] membersWithSkills = getEligibleMembers(id);

            heist = getHeistById(id);
            String[] names = new String[membersWithSkills.length];


            for (int i = 0; i < membersWithSkills.length; i++){
                if(membersWithSkills[i].getStatus().contentEquals("AVAILABLE") ||  membersWithSkills[i].getStatus().contentEquals("RETIRED")) {
                    names[i] = membersWithSkills[i].getName();
                    //heist.setMembers(names);
                }
            }

            JsonNode actualObj = mapper.readTree(request);

            JsonNode arrNode = actualObj.path("members");

            String[] namesFromRequest = new String[arrNode.size()];

            int j = 0;
            if (arrNode.isArray()) {
                for (JsonNode objNode : arrNode){
                    namesFromRequest[j] = objNode.toString().replace("\"","");
                    j++;

                }
            }

            String[] elegibleFromRequest = new String[names.length];

            for(int l = 0; l < namesFromRequest.length; l++) {
                for (int k = 0; k < names.length; k++) {
                    if (namesFromRequest[l].contentEquals(names[k]) ) {
                        elegibleFromRequest[l] = names[k];
                    }
                    else{
                        return new ResponseEntity("Please enter valid members", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            for (int p = 0; p< elegibleFromRequest.length; p++){
                HeistMembers heistMembers = new HeistMembers();
                heistMembers.setHeistId(heist.getId());
                heistMembers.setMemberName(elegibleFromRequest[p]);
                heistMembersRepository.save(heistMembers);
            }


    int nullCounter =0;
            for (int m = 0; m < elegibleFromRequest.length; m++){
                if (elegibleFromRequest[m] == null){
                    nullCounter++;
                }
            }

            String[] realOnes = new String[elegibleFromRequest.length - nullCounter];
            for (int b = 0; b< realOnes.length; b++) {
                for (int v = 0; v < elegibleFromRequest.length; v++) {
                    if (elegibleFromRequest[v] != null) {
                        realOnes[b] =elegibleFromRequest[v];
                    }
                }
            }
            for (int t= 0; t <realOnes.length; t++){
                Member member = memberRepository.findByName(realOnes[t]);
                sendEmail(member.getEmail(),"Heist", member.getName() + "is confirmed for heist");
            }

            Heist heist1 = new Heist();
            heist.setMembers(realOnes);
            System.out.println(realOnes.length);
            heist1 = heistRepository.getById(id);
            heist1.setMembers(realOnes);
            if (heist1.getMembers() != null){
                heist1.setStatus("READY");
            }
            if (heist.getMembers() != null){
                heist.setStatus("READY");
            }
            heistRepository.save(heist1);





        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//      return  heist.getMembers();
        return new ResponseEntity("Members added", HttpStatus.OK);
    }
//
//    // Start the heist manually
        @PutMapping("/heist/{heist_id}/start")
    private ResponseEntity<String> startHeistManually(@PathVariable("heist_id") Long id) throws ParseException {

        Heist heist = heistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No heist with id " + id));


            String endTimeISO = heist.getEndTime();
            if (!heist.getStatus().contentEquals("READY")){
                return new ResponseEntity<String>("Heist is not ready", HttpStatus.BAD_REQUEST);
            }

            Instant now = Instant.now();

            Instant startTime = now;
            //Instant endTime = Instant.parse(endTimeISO);
            Instant endTime = Instant.parse(heist.getEndTime());

            StopWatch stopWatch = new StopWatch();

            Timer timer = new Timer();
            String[] members = heist.getMembers();
            TimerTask startStopwatch = new TimerTask() {
                @Override
                public void run() {
                    stopWatch.start();
                    heist.setStatus("IN_PROGRESS");
                    System.out.println("Stopwatch started");
                    for (int j = 0; j < members.length; j++){
                        Member member1 = memberRepository.findByName(members[j]);
                        sendEmail(member1.getEmail(),"Greetings", "Heist started");
                    }
                }
            };

            TimerTask stopStopwatch = new TimerTask() {
                @Override
                public void run() {
                    stopWatch.stop();
                    heist.setStatus("FINISHED");
                    System.out.println("Stopwatch closed");
                    for (int j = 0; j < members.length; j++){
                        Member member1 = memberRepository.findByName(members[j]);
                        sendEmail(member1.getEmail(),"Greetings", "Heist ended");
                    }
                }
            };
            timer.schedule( startStopwatch, Date.from(startTime.plusSeconds(10)));
            timer.schedule(stopStopwatch, Date.from(endTime));

           if (startTime.isAfter(endTime)){
               return new ResponseEntity<String>("End time is before start time", HttpStatus.BAD_REQUEST);
           }



        return new ResponseEntity<String>("", HttpStatus.OK);
        }

         //Start and finish heist automatically
    public String startingAndFinishingHeistAutomatically(Long id){

        HeistWithSkills heist = getHeistById(id);
        if (!heist.getStatus().contentEquals("READY")){
            return ("Heist is not ready");
        }



        Instant startTime = Instant.parse(heist.getStartTime());
        Instant endTime = Instant.parse(heist.getEndTime());

        // Calculating levelUp
        long startTimeSeconds = startTime.getEpochSecond();
        long endTimeSeconds = endTime.getEpochSecond();
        int durationOfHeist = (int)(endTimeSeconds - startTimeSeconds);
        double levelTUpTime = (double)applicationProperties.getLevelUpTime();
        int numberOfLevelUps = (int)Math.floor(durationOfHeist/levelTUpTime);


        StopWatch stopWatch = new StopWatch();

        String[] names = heist.getMembers();

        Timer timer = new Timer();
        TimerTask startStopwatch = new TimerTask() {
            @Override
            public void run() {
                stopWatch.start();
                heist.setStatus("IN_PROGRESS");
                System.out.println("Stopwatch started");

                // Looking for members that need to be leveled up
               if (durationOfHeist > applicationProperties.getLevelUpTime()){
                   String[] memberNames = heist.getMembers();
                   List<Member> allMembers = memberRepository.findAll();
                   List<SkillsRefactored> memberSkills = skillsRefactoredRepository.findAll();
                   Long[] memberIds = new Long[memberNames.length];
                   for (int i = 0; i < memberNames.length; i++){
                       for (Member member : allMembers){
                           if (memberNames[i] == member.getName()){
                               memberIds[i] = member.getId();
                           }
                       }
                   }

                   for (int j = 0; j < memberIds.length; j++) {
                       for (SkillsRefactored memberSkill : memberSkills) {
                           if (memberIds[j] == memberSkill.getIDMember()){
                               for (int k = 0; k<numberOfLevelUps; k++) {
                                   if (memberSkill.getSkillLevel().length() < 10) {
                                       memberSkill.setSkillLevel(memberSkill.getSkillLevel() + "*");
                                   }
                               }
                           }
                       }
                   }
               }

            }
        };

        TimerTask stopStopwatch = new TimerTask() {
            @Override
            public void run() {
                stopWatch.stop();
                heist.setStatus("FINISHED");
                System.out.println("Stopwatch closed");
            }
        };
        timer.schedule( startStopwatch, Date.from(startTime.plusSeconds(10)));
        timer.schedule(stopStopwatch, Date.from(endTime));
        if (startTime.isAfter(endTime)){
            return ("End time is before start time");
        }

        if (endTime.isBefore(Instant.now())){
            return "Please update your start and end time of the heist!";
        }

        return "";
    }

    // Checking the outcome of the heist
    @GetMapping("/heist/{heist_id}/outcome")
    public ResponseEntity<String> getHeistOutcome(@PathVariable("heist_id") Long id){
        HeistWithSkills heist = getHeistById(id);
        if (heist == null){
           return new ResponseEntity<>("No heist with id" + id, HttpStatus.NOT_FOUND);}
        if(heist.getStatus() != "FINISHED"){
            return new ResponseEntity<>("Heist is not finished!", HttpStatus.METHOD_NOT_ALLOWED);
       }



        List<SkillsRefactoredHeist> heistSkills = skillsRefactoredRepositoryHeist.findByIDHeist(id);

        int requiredMembers = 0;
        for (SkillsRefactoredHeist heistSkill : heistSkills){
            requiredMembers = Integer.parseInt(heistSkill.getMembers());
        }
        Heist heist1 = heistRepository.getById(id);
        int actualMembersLength = heist1.getMembers().length;
        String[] actualMembers = heist.getMembers();

        if ((requiredMembers/2) > actualMembersLength){
            heist.setOutcome("FAILED");
            for (String member : actualMembers){
                Member member1 = memberRepository.findByName(member);
                member1.setStatus("EXPIRED");
            }
        }

        double membersForPunishment = (actualMembersLength/3)*2;
        int counter = (int)membersForPunishment;
        if ((requiredMembers /2) <= actualMembersLength && (requiredMembers * 0.75) > actualMembersLength){
            heist.setOutcome("FAILED");
            int check = 0;
            for (String member : actualMembers){
                Member member1 = memberRepository.findByName(member);
                member1.setStatus("EXPIRED");
                check++;
                if(check >= counter){
                    break;
                }
            }
        }
        double membersForPunishment2 = (actualMembersLength/3);
        int counter1 = (int)membersForPunishment;
        if (actualMembersLength >= (requiredMembers * 0.75)  && (requiredMembers ) > actualMembersLength){
            heist.setOutcome("SUCCEEDED");
            int check = 0;
            for (String member : actualMembers){
                Member member1 = memberRepository.findByName(member);
                member1.setStatus("EXPIRED");
                check++;
                if(check >= counter){
                    break;
                }
            }
        }

        if(actualMembersLength == requiredMembers){
            heist.setOutcome("SUCCEEDED");
        }

        return new ResponseEntity<String>(heist.getOutcome(), HttpStatus.OK);
    }
}
