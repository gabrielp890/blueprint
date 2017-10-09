/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bs = null;

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGet() {
        try {
            return new ResponseEntity<>(bs.getAllBlueprints(), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.GET, path = "/{author}")
    public ResponseEntity<?> manejadorGetByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> bp = bs.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(bp, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>("error en el get por autor", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}/{bpname}")
    public ResponseEntity<?> manejadorGetAuthorYName(@PathVariable String author, @PathVariable String bpname) {
        try {
            return new ResponseEntity<>(bs.getBlueprint(author, bpname), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>("ha ocurrido un error al buscar el author y el nombre", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPost(@RequestBody Blueprint bp) {
        bs.addNewBlueprint(bp);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "{author}/{bpname}")
    public ResponseEntity<?> manejadorput(@PathVariable String author,@PathVariable String name, @RequestBody List<Point> punto){
        try {
            bs.actualizarBP(author, name, punto);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        }
    }

}
