package com.jv01.fonctionals;

import com.jv01.models.InteractionModel;

public class GameEntity {
    public int[] position;
    public String entityType;
    public InteractionModel entityModel;
    public int range;

    public GameEntity(String type) {
        this.entityType = type;
        this.entityModel = GetInteractionModelFromType(type);
    }

    public GameEntity(InteractionModel interactionModel) {
        this.entityModel = interactionModel;
        this.entityType = GetTypeFromInteractionModel(interactionModel);
    }

    public GameEntity(int[] position, String type) {
        this.position = position;
        this.entityType = type;
        this.entityModel = GetInteractionModelFromType(type);
    }

    public GameEntity(int[] position, InteractionModel interactionModel) {
        this.position = position;
        this.entityModel = interactionModel;
        this.entityType = GetTypeFromInteractionModel(interactionModel);
    }

    public GameEntity(int[] position, String type, int distance) {
        this.position = position;
        this.entityType = type;
        this.entityModel = GetInteractionModelFromType(type);
        this.range = distance;
    }

    public GameEntity(int[] position, InteractionModel interactionModel, int distance) {
        this.position = position;
        this.entityModel = interactionModel;
        this.entityType = GetTypeFromInteractionModel(interactionModel);
        this.range = distance;
    }

    public InteractionModel GetInteractionModelFromType(String type) {
        switch (type) {
            case "item":
                return InteractionModel.ITEMS;
            case "building":
                return InteractionModel.BUILDINGS;
            case "npc":
                return InteractionModel.NPCS;
            case "merchant":
                return InteractionModel.MERCHANTS;
            case "tool":
                return InteractionModel.TOOLS;
            case "arcade":
                return InteractionModel.ARCADES;
            default:
                return InteractionModel.UNDEFINED;
        }
    }

    public String GetTypeFromInteractionModel(InteractionModel interactionModel) {
        switch (interactionModel) {
            case ITEMS:
                return "item";
            case BUILDINGS:
                return "building";
            case NPCS:
                return "npc";
            case MERCHANTS:
                return "merchant";
            case TOOLS:
                return "tool";
            case ARCADES:
                return "arcade";
            default:
                return "undefined";
        }
    }
}