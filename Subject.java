public class Subject {
    int id;
    int gender;
    double picvocab;
    double procspeed;
    double angaffect;
    double anghostile;
    double fearaffect;
    double fearsomatic;
    double sadness;
    double lifesatis;
    double friendship;
    double loneliness;

    public Subject (int sid, int sgender, double spicvocab, double sprocspeed, double sangaffect, double sanghostile, double sfearaffect, double sfearsomatic, double ssadness, double slifesatis, double sfriendship, double sloneliness) {
        this.id = sid;
        this.gender = sgender;
        this.picvocab = spicvocab;
        this.procspeed = sprocspeed;
        this.angaffect = sangaffect;
        this.anghostile = sanghostile;
        this.fearaffect = sfearaffect;
        this.fearsomatic = sfearsomatic;
        this.sadness = ssadness;
        this.lifesatis = slifesatis;
        this.friendship = sfriendship;
        this.loneliness = sloneliness;
    }
}
