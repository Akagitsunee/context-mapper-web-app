import { withLatestFrom } from 'rxjs';

export const deleteDownloadsFolder = () => {
  const downloadsFolder = Cypress.config('downloadsFolder')
  cy.task('deleteFolder', downloadsFolder)
}

const path = require('path');
describe('gui', function () {
  it('test generation and export', function () {
    cy.viewport(1920, 1009);
    cy.visit('http://localhost:4200/');
    cy.wait(1000);
    cy.get('.ace_text-input')
      .first()
      .focus()
      .clear()
      .type(
        'ContextMap Sample {\n contains Sample1\n contains Sample2\n Sample1 [SK]<->[SK] Sample2 } \n BoundedContext Sample1 \nBoundedContext Sample2',
        { parseSpecialCharSequences: false }
      );

    cy.get('.editor > .field-header > button.btn').click();
    cy.wait(1000);
    cy.get('.generate > .field-header > .select').select('png');
    cy.get('.generate > .field-header > .btn').click();

    const downloadsFolder = Cypress.config('downloadsFolder');
    const downloadedFilename = path.join(downloadsFolder, 'contextmap.png');

    cy.readFile(downloadedFilename, 'binary', { timeout: 15000 }).should(
      (buffer) => expect(buffer.length).to.be.gt(100)
    );
  });
});
