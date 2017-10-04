/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts = new Point[]{new Point(140, 140), new Point(115, 115)};
        Blueprint bp = new Blueprint("_authorname_", "_bpname_", pts);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);

        Point[] pts1 = new Point[]{new Point(10, 14), new Point(215, 215)};
        
        Blueprint bp1 = new Blueprint("pedro", "zue", pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        
        Blueprint bp2 = new Blueprint("pedro", "fury", pts1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);

        Point[] pts2 = new Point[]{new Point(180, 130), new Point(125, 195)};
        Blueprint bp4 = new Blueprint("marcos", "vc", pts2);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);

        Point[] pts3 = new Point[]{new Point(168, 184), new Point(135, 175)};
        Blueprint bp3 = new Blueprint("pablo", "paintPa", pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {

        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> temp = new LinkedHashSet<>();
        for (Tuple<String, String> entry : blueprints.keySet()) {

            if (entry.getElem1().equals(author)) {
                temp.add(blueprints.get(entry));
            }
        }
//        System.out.println("testbyau"+ temp);
        return temp;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        HashSet<Blueprint> temp = new HashSet<>();
        for (Blueprint bp : blueprints.values()) {
            temp.add(bp);
        }
//        System.out.println("test1111 "+temp);
        return temp;
    }

    @Override
    public void actualizarBP(String author, String name, Point punto) throws BlueprintNotFoundException {
        if(blueprints.containsKey(new Tuple<>(author,name))){
            blueprints.get(new Tuple<>(author,name)).addPoint(punto);
        }else{
            throw new UnsupportedOperationException("el plano que esta intentancdo actualizar no se encuentra registrado");
        }
    }

    @Override
    public void actualizarBPArray(String author, String name, List<Point> punto) throws BlueprintNotFoundException {
        if(blueprints.containsKey(new Tuple<>(author,name))){
            Blueprint b=blueprints.get(new Tuple<>(author,name));
            for(Point p:punto){
                b.addPoint(p);
            }
        }else{
        throw new UnsupportedOperationException("el plano que esta intentancdo actualizar no se encuentra registrado"); //To change body of generated methods, choose Tools | Templates.
    }
    }

}
