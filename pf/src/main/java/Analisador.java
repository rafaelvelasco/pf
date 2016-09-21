import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/*
 * Bugs conhecidos:
 * 
 * - Ao dar conflito, lança exceção e interrompe execução do programa.
 * - Não está apagando as branches criadas para teste.
 */
public class Analisador {

	public static void main(String[] args) throws IOException, InvalidRefNameException, GitAPIException {
		Repository repository = new FileRepository("/home/rodrigorgs/local/git/RxJava/.git");
		
		Git git = new Git(repository);
		Iterable<RevCommit> commits = git.log().all().call();
		int count = 0;
		for (RevCommit commit : commits) {
			if (commit.getParentCount() == 2) {
				RevCommit pai1 = commit.getParent(0);
				RevCommit pai2 = commit.getParent(1);
				
				System.out.println("LogCommit: " + commit.getShortMessage());
				String branchName = "testec" + count;
				git.checkout().setStartPoint(pai1).setCreateBranch(true).setName(branchName).call();
				MergeResult result = git.merge().include(pai2).call();
				System.out.println(result);
				
				git.checkout().setStartPoint(commit).setCreateBranch(false).setForce(true).setName(commit.name()).call();
				git.branchDelete().setBranchNames(branchName);

				System.out.println();
				count++;
			}
		}
		System.out.println(count);
		git.close();
	}

}
