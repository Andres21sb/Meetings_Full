/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.am.preguntas_backend.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Escinf
 */
public class Service{
    private static Service uniqueInstance;
    
    public static Service instance(){
        if (uniqueInstance == null){
            uniqueInstance = new Service();
        }
        return uniqueInstance; 
    }

    HashMap<String,User> users;
    HashMap<String,Meeting> meetings;
    int meeting_autonumber=0;
    
    private Service(){
        users = new HashMap();
        meetings = new HashMap();
        
        User u;
        u=new User("mfreeman@hollywood.com", "Morgan Freeman","1","CLI");
        users.put(u.getEmail(),u);
        u=new User("scalle@hollywood.com", "Sasha Calle","2","CLI");
        users.put(u.getEmail(),u);
        
        Meeting m;
        m=new Meeting(users.get("mfreeman@hollywood.com"), nextMeeting(), "Plan new movie", "25/06/2023","PUBLISHED");
        m.getContacts().add(new Contact("mpfeiffer@hollywood.com", "Michelle Pfeiffer"));
        m.getContacts().add(new Contact("jroberts@gmail.com", "Julia Roberts"));
        meetings.put(m.getId(), m);
        
        m=new Meeting(users.get("mfreeman@hollywood.com"), nextMeeting(), "Party", "24/06/2023","OVERDUE");
        m.getContacts().add(new Contact("jroberts@gmail.com", "Julia Roberts"));
        m.getContacts().add(new Contact("tcruise@hollywood.com", "Tom Cruise"));        
        meetings.put(m.getId(), m);
        
        m=new Meeting(users.get("mfreeman@hollywood.com"), nextMeeting(), "Plot review", "27/06/2023","UPCOMING");
        m.getContacts().add(new Contact("jroberts@gmail.com", "Julia Roberts"));
        meetings.put(m.getId(), m);        
        
        m=new Meeting(users.get("scalle@hollywood.com"), nextMeeting(), "Rehearsal", "28/06/2023","UPCOMING");
        m.getContacts().add(new Contact("emiller@hollywood.com", "Esra Miller"));
        m.getContacts().add(new Contact("mkeaton@gmail.com", "Michael Keaton"));
        meetings.put(m.getId(), m);        

     }
    
    String nextMeeting(){
        return (meeting_autonumber++)+"";
    }

    public User user_read(String email)throws Exception{
        User user = users.get(email);
        if (user!=null) return user;
        else throw new Exception("User does not exist");
    }
 
    public List<Meeting> meetings_by_user(User user) {
        return meetings.values().stream().
            filter( m-> m.getOwner().getEmail().equals(user.getEmail())).
            collect(Collectors.toList());
    }
    
    public void meetings_add(Meeting meeting ) throws Exception{
        if(users.get(meeting.owner.getEmail())==null) 
            throw new Exception("Owner does not exist");
        meeting.setId(nextMeeting());
        meetings.put(meeting.getId(), meeting);
    }
    
    public void meetings_delete(String id ){
        meetings.remove(id);
    }    
    
    public List<Contact> contacts_by_user(User user){
        List<Contact> result = new ArrayList<>();
        for(Meeting m:this.meetings_by_user(user))
            for(Contact c:m.getContacts())
                if (!result.contains(c)) result.add(c);
        return result;
    }
    
}