package net.sf.anathema.character.reporting.pdf;

import com.google.common.base.Joiner;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.sf.anathema.character.generic.GenericCharacter;
import net.sf.anathema.character.generic.framework.magic.view.CharmDescriptionProviderExtractor;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.IMagic;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.generic.magic.description.MagicDescription;
import net.sf.anathema.character.main.model.charms.CharmsModelFetcher;
import net.sf.anathema.character.main.model.experience.ExperienceModelFetcher;
import net.sf.anathema.character.main.model.spells.SpellsModelFetcher;
import net.sf.anathema.character.reporting.pdf.content.stats.magic.CharmStats;
import net.sf.anathema.character.reporting.pdf.content.stats.magic.SpellStats;
import net.sf.anathema.charmtree.builder.MagicDisplayLabeler;
import net.sf.anathema.charmtree.builder.stringbuilder.ScreenDisplayInfoStringBuilder;
import net.sf.anathema.charmtree.builder.stringbuilder.source.MagicSourceStringBuilder;
import net.sf.anathema.charmtree.builder.stringbuilder.type.VerboseCharmTypeStringBuilder;
import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.reporting.ReportException;
import net.sf.anathema.framework.reporting.pdf.AbstractPdfReport;
import net.sf.anathema.framework.reporting.pdf.PdfReportUtils;
import net.sf.anathema.framework.repository.Item;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.resources.Resources;

import static java.text.MessageFormat.format;

public class MagicReport extends AbstractPdfReport {

  private final Resources resources;
  private final IApplicationModel model;
  private final MagicPartFactory partFactory;

  public MagicReport(Resources resources, IApplicationModel model) {
    this.resources = resources;
    this.model = model;
    partFactory = new MagicPartFactory(new PdfReportUtils());
  }

  @Override
  public String toString() {
    return resources.getString("MagicReport.Name");
  }

  @Override
  public void performPrint(Item item, Document document, PdfWriter writer) throws ReportException {
    MultiColumnText columnText = new MultiColumnText(document.top() - document.bottom() - 15);
    columnText.addRegularColumns(document.left(), document.right(), 20, 2);
    Hero character = (Hero) item.getItemData();
    try {
      printCharms(columnText, character);
      printSpells(columnText, character);
      writeColumnText(document, columnText);
    } catch (DocumentException e) {
      throw new ReportException(e);
    }
  }

  private void printSpells(MultiColumnText columnText, Hero hero) throws DocumentException {
    String currentGroup = "";
    for (ISpell spell : getCurrentSpells(hero)) {
      SpellStats spellStats = createSpellStats(spell);
      String nextGroupName = format("{0} {1}", spellStats.getType(resources), spellStats.getGroupName(resources));
      if (!currentGroup.equals(nextGroupName)) {
        currentGroup = nextGroupName;
        columnText.addElement(partFactory.createGroupTitle(currentGroup));
      }
      addMagicName(spell, columnText);
      addSpellCost(spell, columnText);
      addSpellTarget(spellStats, columnText);
      addCharmDescription(spell, columnText);
    }
  }

  public void printCharms(MultiColumnText columnText, Hero hero) throws DocumentException {
    String currentGroup = "";
    for (ICharm charm : getCurrentCharms(hero)) {
      CharmStats charmStats = createCharmStats(hero, charm);
      if (!currentGroup.equals(charmStats.getGroupName(resources))) {
        currentGroup = charmStats.getGroupName(resources);
        columnText.addElement(partFactory.createGroupTitle(currentGroup));
      }
      addMagicName(charm, columnText);
      addCharmData(charmStats, charm, columnText);
      addCharmDescription(charm, columnText);
    }
  }

  private void addSpellCost(ISpell charm, MultiColumnText columnText) throws DocumentException {
    String costsLabel = resources.getString("MagicReport.Costs.Label") + ": ";
    String costsValue = new ScreenDisplayInfoStringBuilder(resources).createCostString(charm);
    columnText.addElement(partFactory.createDataPhrase(costsLabel, costsValue));
  }

  private void addSpellTarget(SpellStats spellStats, MultiColumnText columnText) throws DocumentException {
    String targetLabel = resources.getString("MagicReport.Target.Label") + ": ";
    String target = Joiner.on(", ").join(spellStats.getDetailStrings(resources));
    columnText.addElement(partFactory.createDataPhrase(targetLabel, target));
  }

  private void addMagicName(IMagic magic, MultiColumnText columnText) throws DocumentException {
    String charmName = new MagicDisplayLabeler(resources).getLabelForMagic(magic);
    columnText.addElement(partFactory.createCharmTitle(charmName));
  }

  private void addCharmData(CharmStats charmStats, ICharm charm, MultiColumnText columnText) throws DocumentException {
    PdfPTable table = partFactory.createDataTable();
    addCostsCell(charm, table);
    addTypeCell(charm, table);
    addKeywordsRow(charmStats, table);
    addDurationRow(charmStats, table);
    columnText.addElement(table);
  }

  private void addCostsCell(ICharm charm, PdfPTable table) {
    String costsLabel = resources.getString("MagicReport.Costs.Label") + ": ";
    String costsValue = new ScreenDisplayInfoStringBuilder(resources).createCostString(charm);
    table.addCell(partFactory.createDataCell(costsLabel, costsValue));
  }

  private void addTypeCell(ICharm charm, PdfPTable table) {
    String typeLabel = resources.getString("MagicReport.Type.Label") + ": ";
    String typeValue = new VerboseCharmTypeStringBuilder(resources).createTypeString(charm.getCharmTypeModel());
    table.addCell(partFactory.createDataCell(typeLabel, typeValue));
  }

  private void addKeywordsRow(CharmStats charmStats, PdfPTable table) {
    String keywords = Joiner.on(", ").join(charmStats.getDetailStrings(resources));
    String keywordsLabel = resources.getString("MagicReport.Keywords.Label") + ": ";
    table.addCell(partFactory.createDoubleDataCell(keywordsLabel, keywords));
  }

  private void addDurationRow(CharmStats charmStats, PdfPTable table) {
    String durationLabel = resources.getString("MagicReport.Duration.Label") + ": ";
    String durationString = charmStats.getDurationString(resources);
    table.addCell(partFactory.createDoubleDataCell(durationLabel, durationString));
  }

  private void addCharmDescription(IMagic magic, MultiColumnText columnText) throws DocumentException {
    MagicDescription charmDescription = getCharmDescription(magic);
    if (charmDescription.isEmpty()) {
      String sourceString = new MagicSourceStringBuilder<>(resources).createSourceString(magic);
      String sourceReference = resources.getString("MagicReport.See.Source", sourceString);
      columnText.addElement(partFactory.createDescriptionParagraph(sourceReference));
    }
    for (String paragraph : charmDescription.getParagraphs()) {
      columnText.addElement(partFactory.createDescriptionParagraph(paragraph));
    }
  }

  private CharmStats createCharmStats(Hero hero, ICharm charm) {
    return new CharmStats(charm, new GenericCharacter(hero));
  }

  private SpellStats createSpellStats(ISpell spell) {
    return new SpellStats(spell);
  }

  private MagicDescription getCharmDescription(IMagic magic) {
    return CharmDescriptionProviderExtractor.CreateFor(model, resources).getCharmDescription(magic);
  }

  private void writeColumnText(Document document, MultiColumnText columnText) throws DocumentException {
    do {
      document.add(columnText);
      columnText.nextColumn();
    } while (columnText.isOverflow());
  }

  @Override
  public boolean supports(Item item) {
    if (item == null || !(item.getItemData() instanceof Hero)) {
      return false;
    }
    Hero hero = (Hero) item.getItemData();
    return getCurrentCharms(hero).length > 0;
  }

  private ISpell[] getCurrentSpells(Hero hero) {
    boolean experienced = ExperienceModelFetcher.fetch(hero).isExperienced();
    return SpellsModelFetcher.fetch(hero).getLearnedSpells(experienced);
  }

  private ICharm[] getCurrentCharms(Hero hero) {
    boolean experienced = ExperienceModelFetcher.fetch(hero).isExperienced();
    return CharmsModelFetcher.fetch(hero).getLearnedCharms(experienced);
  }
}
