package kr.hanjari.backend.domain.club.presentation.dto.util;

import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.presentation.dto.request.CategoryRequest;
import kr.hanjari.backend.domain.common.command.CategoryCommand;

public class CategoryUtils {

  public static void validate(
      ClubType clubType,
      CategoryRequest category
  ) {

    if (clubType == null) {
      throw new IllegalArgumentException("clubType is required.");
    }
    if (category == null) {
      throw new IllegalArgumentException("category is required.");
    }

    switch (clubType) {
      case CENTRAL -> { // Central Club
        requireNonNull(category.central(), "For CENTRAL type, centralCategory is required.");
        requireNull(category.union(), "For CENTRAL type, unionCategory cannot be specified.");
        requireNull(category.college(), "For CENTRAL type, college cannot be specified.");
        requireNull(category.department(), "For CENTRAL type, department cannot be specified.");
      }
      case UNION -> { // Union Club
        requireNonNull(category.union(), "For UNION type, unionCategory is required.");
        requireNull(category.central(), "For UNION type, centralCategory cannot be specified.");
        requireNull(category.college(), "For UNION type, college cannot be specified.");
        requireNull(category.department(), "For UNION type, department cannot be specified.");
      }
      case COLLEGE -> { // College Club (no department info)
        requireNonNull(category.college(), "For COLLEGE type, college is required.");
        requireNull(category.central(), "For COLLEGE type, centralCategory cannot be specified.");
        requireNull(category.union(), "For COLLEGE type, unionCategory cannot be specified.");
        requireNull(category.department(), "For COLLEGE type, department cannot be specified.");
      }
      case DEPARTMENT -> { // Department Club (with department info)
        requireNull(category.union(), "For DEPARTMENT type, unionCategory cannot be specified.");
        requireNull(category.central(), "For DEPARTMENT type, centralCategory cannot be specified.");
        requireNonNull(category.college(), "For DEPARTMENT type, college is required.");
        requireNonNull(category.department(), "For DEPARTMENT type, department is required.");
      }
    }
  }

  private static void requireNonNull(Object o, String m) {
    if (o == null) {
      throw new IllegalArgumentException(m);
    }
  }

  private static void requireNull(Object o, String m) {
    if (o != null) {
      throw new IllegalArgumentException(m);
    }
  }

  public static CategoryCommand toCategoryCommand(
      ClubType clubType,
      CategoryRequest category
  ) {
    return new CategoryCommand(
        clubType,
        category.central(),
        category.union(),
        category.college(),
        category.department()
    );
  }
}
