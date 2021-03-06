package model;

import model.exceptions.InvalidIndexException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a journal containing tennis matches
// Note: The toJson() and matchesToJson() methods in this class are largely
//       based on the methods with the same names found in the WorkRoom class
//       of the WorkRoom application provided to us on GitHub.
public class TennisMatchJournal implements Writable {
    private final List<TennisMatch> journal;

    // EFFECTS: Instantiates a TennisMatchJournal as an empty ArrayList
    public TennisMatchJournal() {
        journal = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given tennis match into the journal; do nothing if it has already
    //          been added
    public void addMatch(TennisMatch match) {
        if (!journal.contains(match)) {
            journal.add(match);
        }
    }

    // EFFECTS: return true if match is in the journal; otherwise, return false
    public boolean containsMatch(TennisMatch match) {
        return journal.contains(match);
    }

    // EFFECTS: return the number of matches in the Journal
    public int journalLength() {
        return journal.size();
    }

    // MODIFIES: this
    // EFFECTS: deletes the given tennis match from the journal; does nothing if journal
    //          does not contain the given tennis match
    public void deleteMatch(TennisMatch match) {
        journal.remove(match);
    }

    // EFFECTS: prints out a list of all the tennis matches in the journal;
    //          if journal is empty, prints out a warning to the user
    public String viewJournal() {
        if (journal.isEmpty()) {
            return viewJournalEmpty();
        } else {
            return viewJournalNotEmpty();
        }
    }

    // EFFECTS: prints out a warning to the user telling them that their
    //          journal is empty
    private String viewJournalEmpty() {
        return "<YOUR JOURNAL IS EMPTY>";
    }

    // EFFECTS: prints out a list of all the tennis matches in the journal
    private String viewJournalNotEmpty() {
        StringBuilder result = new StringBuilder();

        for (TennisMatch match : journal) {
            String opponent = match.getMatchDetails().getOpponent();
            String isWon = (match.getMatchDetails().getIsWon()) ? "WIN" : "LOSS";
            String surface = match.getMatchDetails().getSurface().toUpperCase();
            String duration = match.getMatchDetails().getDuration() + " minutes";
            String date = match.getMatchDetails().getDate();
            String score = match.getMatchStats().getScore();
            int aces = match.getMatchStats().getAces();
            int doubleFaults = match.getMatchStats().getDoubleFaults();
            int winners = match.getMatchStats().getWinners();
            int unforcedErrors = match.getMatchStats().getUnforcedErrors();

            result.append("\n<DETAILS>")
                    .append("\n\tOpponent: ").append(opponent).append("\n\tOutcome: ").append(isWon)
                    .append("\n\tSurface: ").append(surface).append("\n\tDuration: ").append(duration)
                    .append("\n\tDate: ").append(date).append("\n<STATS>").append("\n\tScore: ").append(score)
                    .append("\n\tAces: ").append(aces).append("\n\tDouble Faults: ").append(doubleFaults)
                    .append("\n\tWinners: ").append(winners).append("\n\tUnforced Errors: ").append(unforcedErrors)
                    .append("\n");
        }
        return result.toString();
    }

    // EFFECTS: returns the win loss ratio in the form "wins : losses";
    public String viewWinLossRatio() {
        int wins = 0;
        int losses = 0;

        for (TennisMatch match : journal) {
            boolean isWon = match.getMatchDetails().getIsWon();
            if (isWon) {
                wins++;
            } else {
                losses++;
            }
        }
        return wins + " : " + losses;
    }

    // EFFECTS: gets the journal
    public List<TennisMatch> getJournal() {
        return journal;
    }

    @Override
    // EFFECTS: returns journal as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("matches", matchesToJson());
        return json;
    }

    // EFFECTS: returns the TennisMatch at the specified index
    //          (throws InvalidIndexException if index is not
    //          in the range [0, journalLength() - 1])
    public TennisMatch getMatchAt(int index) throws InvalidIndexException {
        if (!(index >= 0) || !(index <= journalLength() - 1)) {
            throw new InvalidIndexException();
        } else {
            return journal.get(index);
        }
    }

    // EFFECTS: returns matches in journal as a JSON array
    private JSONArray matchesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TennisMatch match : journal) {
            jsonArray.put(match.toJson());
        }

        return jsonArray;
    }
}

































