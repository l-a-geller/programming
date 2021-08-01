package tools.execution;

import data.HumanBeing;

import java.util.Comparator;

public class DefaultComparator implements Comparator<HumanBeing> {
    @Override
    public int compare(HumanBeing human1, HumanBeing human2) {
        if (human1.getImpactSpeed() != human2.getImpactSpeed()) return (int) (human1.getImpactSpeed() - human2.getImpactSpeed());
        if (human1.doesHaveToothpick() && !human2.doesHaveToothpick()) return 1;
        if (!human1.doesHaveToothpick() && human2.doesHaveToothpick()) return -1;
        if (human1.getRealHero() && !human2.getRealHero()) return 1;
        if (!human1.getRealHero() && human2.getRealHero()) return -1;
        if (human1.getWeaponType() != null && human2.getWeaponType() == null) return 1;
        if (human1.getWeaponType() == null && human2.getWeaponType() != null) return -1;
        if (human1.getWeaponType() != null && human2.getWeaponType() != null && human1.getWeaponType().ordinal() != human2.getWeaponType().ordinal()) return human1.getWeaponType().ordinal() - human2.getWeaponType().ordinal();
        if (human1.getMood().ordinal() != human2.getMood().ordinal()) return human1.getMood().ordinal() - human2.getMood().ordinal();
        if (human1.getCar().isCool() != human2.getCar().isCool()) {
            if (human1.getCar().isCool()) return 1;
            if (human2.getCar().isCool()) return -1;
        }
        if (!human1.getCreationDate().equals(human2.getCreationDate())) return human1.getCreationDate().compareTo(human2.getCreationDate());
        long coords1 = human1.getCoordinates().getX() * human1.getCoordinates().getX() + human1.getCoordinates().getY() * human1.getCoordinates().getY();
        long coords2 = human2.getCoordinates().getX() * human2.getCoordinates().getX() + human2.getCoordinates().getY() * human2.getCoordinates().getY();
        if (coords1 != coords2) return (int)(coords1 - coords2);
        if (!human1.getName().equals(human2.getName())) return human1.getName().compareTo(human2.getName());
        return (int)(human1.getId() - human2.getId());
    }
}
