package com.hhuebner.autogp.core.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hhuebner.autogp.core.InputHandler;
import com.hhuebner.autogp.core.engine.AnchorPoint;
import com.hhuebner.autogp.core.engine.BoundingBox;
import com.hhuebner.autogp.core.engine.Room;
import com.hhuebner.autogp.core.util.Direction;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class RoomComponent extends InteractableComponent {

    public Room room;
    public List<PlanComponent> children = new ArrayList<>();

    public RoomComponent() {
        super(null, null, 0);
    }

    public RoomComponent(Room room, BoundingBox bb, long id) {
        super(bb, room.name, id);
        this.room = room;
    }

    @Override
    public  void render(GraphicsContext ctx, InputHandler inputHandler) {
        for(PlanComponent component : this.children) {
            component.render(ctx, inputHandler);
        }
    }

    @JsonIgnore
    public List<AnchorPoint> getAnchors(List<RoomComponent> graph) {
        List<AnchorPoint> list = new ArrayList<>();

        for(Direction facing : Direction.values()) {
            AnchorPoint aCW = AnchorPoint.create(graph, this, facing.rotateCW(), facing);
            AnchorPoint aCCW = AnchorPoint.create(graph, this, facing.rotateCCW(), facing);
            if(aCW != null) list.add(aCW);
            if(aCCW != null) list.add(aCCW);
        }

        return list;
    }

    public void addChild(PlanComponent component) {
        this.children.add(component);
    }

    public List<PlanComponent> getChildren() {
        return this.children;
    }

    @JsonIgnore
    public WallComponent getWallComponent() {
        for(PlanComponent c : children) {
            if(c instanceof  WallComponent)
                return (WallComponent) c;
        }
        throw new ExceptionInInitializerError();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomComponent that = (RoomComponent) o;
        return this.id == that.id;
    }
}
