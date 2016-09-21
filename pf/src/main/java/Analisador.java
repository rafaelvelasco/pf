import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class Analisador {

	public static void main(String[] args) throws IOException, InvalidRefNameException, GitAPIException {
		Repository repository = new FileRepository("/home/rodrigorgs/local/git/RxJava/.git");

		Git git = new Git(repository);
		Iterable<RevCommit> commits = git.log().all().call();
		int count = 0;
		for (RevCommit commit : commits) {
			if (commit.getParentCount() == 2) {
				System.out.println("LogCommit: " + commit.getShortMessage());
				count++;
			}
		}
		System.out.println(count);
		git.close();
	}

}
