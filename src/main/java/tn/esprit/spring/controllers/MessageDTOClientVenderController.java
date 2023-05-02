package tn.esprit.spring.controllers;

import com.pusher.rest.Pusher;
import io.swagger.annotations.Api;
import jdk.management.resource.ResourceRequestDeniedException;
import net.bytebuddy.asm.Advice;
import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.MessageDTOClientVender;
import tn.esprit.spring.exception.ResourceNotFoundException;
import tn.esprit.spring.repository.MessageDTOClientVenderRepository;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "Chat")
@RequestMapping("api/user/client/Chat")
public class MessageDTOClientVenderController {
    @Autowired
    private MessageDTOClientVenderRepository messageDTOClientVenderRepository;

    //get all employees
    @GetMapping("/messages")
    public List<MessageDTOClientVender> getAllMessage(){
        return messageDTOClientVenderRepository.findAll();
    }

    //get message by id

    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageDTOClientVender> getMessagesById(@PathVariable long id) {

        MessageDTOClientVender messages = messageDTOClientVenderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Messages not exist with id :" + id));

        return ResponseEntity.ok(messages);
    }

    //Chatt REAL
    @PostMapping("/chat/{idClient}/{idVendor}/Message")

    public List<MessageDTOClientVender> fun( @PathVariable long idClient ,@PathVariable long idVendor , @PathVariable String message ) {
        Pusher pusher = new Pusher("1559792", "24c24bdac63ad4a14fdf", "f7982ee0c108327e333d");
        MessageDTOClientVender messageDTO = new MessageDTOClientVender();
        messageDTO.setMessage(message);
        messageDTO.setSender(idClient);
        messageDTO.setRceiver(idVendor);
        messageDTOClientVenderRepository.save(messageDTO);

            pusher.setCluster("eu");
            pusher.setEncrypted(true);

            long sender = messageDTO.getSender();

            pusher.trigger("chat", "message", sender);

            return comunicationCllientVendorr(idClient,idVendor);

    }
    @GetMapping("/chatClientVendor/{idClient}/{idVendor}")
    public List<MessageDTOClientVender> comunicationCllientVendor(@PathVariable long idClient ,@PathVariable long idVendor){

        List<MessageDTOClientVender> listeMessage = new ArrayList<>();
        for (MessageDTOClientVender m :  messageDTOClientVenderRepository.findAll()){
            if (((m.getSender() == idClient) && (m.getRceiver() == idVendor ) )||((m.getSender() == idVendor) && (m.getRceiver() == idClient ))){
                listeMessage.add(m);
            }
        }
        return listeMessage;
    }
    public List<MessageDTOClientVender> comunicationCllientVendorr( long idClient , long idVendor){

        List<MessageDTOClientVender> listeMessage = new ArrayList<>();
        for (MessageDTOClientVender m :  messageDTOClientVenderRepository.findAll()){
            if (((m.getSender() == idClient) && (m.getRceiver() == idVendor ) )||((m.getSender() == idVendor) && (m.getRceiver() == idClient ))){
                listeMessage.add(m);
            }
        }
        return listeMessage;
    }
}
